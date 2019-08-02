package org.creativemines.schemaorg4j.codegen.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SchemaGraph {

    private static final Logger LOGGER = LoggerFactory.getLogger(SchemaGraph.class);

    private Map<String, SchemaClass> classesById;
    private Map<String, Set<SchemaClass>> superClassById;
    private Map<String, Set<SchemaProperty>> propertiesByClassId;
    private Map<String, Set<SchemaEnumMember>> enumMembersByClassId;

    private Map<String, Set<SchemaClass>> waitingForSuperclassById;

    public SchemaGraph() {
        classesById = new HashMap<>();
        superClassById = new HashMap<>();
        propertiesByClassId = new HashMap<>();
        enumMembersByClassId = new HashMap<>();
        waitingForSuperclassById = new HashMap<>();
    }

    public SchemaClass getClass(String id) {
        return classesById.get(id);
    }

    public Collection<SchemaClass> getClasses() {
        return classesById.values();
    }

    public void addClass(SchemaClass schemaClass) {
        classesById.put(schemaClass.getId(), schemaClass);

        if (waitingForSuperclassById.containsKey(schemaClass.getId())) {
            // One or more objects was waiting for this to be added as a super class
            for (SchemaClass awaitingParent : waitingForSuperclassById.get(schemaClass.getId())) {
                addToSuperClassById(awaitingParent.getId(), schemaClass);
            }
            waitingForSuperclassById.remove(schemaClass.getId());
        }

        for (String id : schemaClass.getSubclassOfIds()) {
            if (classesById.containsKey(id)) {
                addToSuperClassById(schemaClass.getId(), classesById.get(id));
            } else {
                // We need to wait on the superclass to get in
                if (waitingForSuperclassById.containsKey(id)) {
                    // We have a set for this object already, just add it
                    waitingForSuperclassById.get(id).add(schemaClass);
                } else {
                    // We don't have a set yet, create one
                    waitingForSuperclassById.put(id, new HashSet<SchemaClass>() {{
                        add(schemaClass);
                    }});
                }
            }
        }
    }

    private void addToSuperClassById(String childId, SchemaClass parent) {
        // The superclass for this object is already in
        if (superClassById.containsKey(childId)) {
            // We have a set for this object already, just add it
            superClassById.get(childId).add(parent);
        } else {
            // We don't have a set, create a new one
            superClassById.put(childId, new HashSet<SchemaClass>() {{
                add(parent);
            }});
        }

    }

    public Set<SchemaClass> getSuperclasses(String classId) {
        return superClassById.getOrDefault(classId, Collections.emptySet());
    }

    public void addProperty(SchemaProperty property) {
        for (String classId : property.getDomainIncludesIds()) {
            if (propertiesByClassId.containsKey(classId)) {
                propertiesByClassId.get(classId).add(property);
            } else {
                propertiesByClassId.put(classId, new HashSet<SchemaProperty>() {{
                    add(property);
                }});
            }
        }
    }

    public Set<SchemaProperty> getProperties(String id) {
        return propertiesByClassId.getOrDefault(id, Collections.emptySet());
    }

    public void addEnumMember(SchemaEnumMember schemaEnumMember) {
        if (enumMembersByClassId.containsKey(schemaEnumMember.getEnumId())) {
            enumMembersByClassId.get(schemaEnumMember.getEnumId()).add(schemaEnumMember);
        } else {
            enumMembersByClassId
                .put(schemaEnumMember.getEnumId(), new HashSet<SchemaEnumMember>() {{
                    add(schemaEnumMember);
                }});
        }
    }

    public Set<SchemaEnumMember> getEnumMembers(String id) {
        return enumMembersByClassId.getOrDefault(id, Collections.emptySet());
    }

    public void finalizeGraph() {

        List<SchemaClass> removedClasses = new ArrayList<>();

        waitingForSuperclassById.values().forEach(setOfClassesToRemove -> {
            setOfClassesToRemove.forEach(classToRemove -> {
                SchemaClass schemaClass = classesById.remove(classToRemove.getId());
                LOGGER.info("Removed {} from graph (waiting for superclass)", schemaClass.getId());
                removedClasses.add(schemaClass);
            });
        });

        while (!removedClasses.isEmpty()) {
            SchemaClass parentClass = removedClasses.remove(0);

            if (parentClass == null) {
                continue;
            }

            Set<SchemaClass> classes = new HashSet<>(classesById.values());

            classes.forEach(schemaClass -> {
                if (schemaClass.getSubclassOfIds().contains(parentClass.getId())) {
                    SchemaClass removedClass = classesById.remove(schemaClass.getId());

                    LOGGER.info("Removed {} from graph (waiting for superclass)", schemaClass.getId());

                    removedClasses.add(removedClass);
                }
            });
        }
    }

}
