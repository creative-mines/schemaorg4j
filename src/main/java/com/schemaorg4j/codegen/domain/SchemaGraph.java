package com.schemaorg4j.codegen.domain;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SchemaGraph {

    private Map<String, SchemaClass> classesById;
    private Map<String, Set<SchemaClass>> superClassById;
    private Map<String, List<SchemaProperty>> propertiesByClassId;
    private Map<String, List<SchemaEnumMember>> enumMembersByClassId;

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

    public void addClass(SchemaClass schemaClass) {
        classesById.put(schemaClass.getId(), schemaClass);

        if (waitingForSuperclassById.containsKey(schemaClass.getId())) {
            // One or more objects was waiting for this to be added as a super class
            for (SchemaClass awaitingParent : waitingForSuperclassById.get(schemaClass.getId())) {
                addToSuperClassById(awaitingParent.getId(), schemaClass);
            }
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
        return superClassById.getOrDefault(classId, null);
    }

    public boolean isWaitingForSuperClass(String classId) {
        return false;
    }


}
