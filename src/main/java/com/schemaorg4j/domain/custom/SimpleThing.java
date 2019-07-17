package com.schemaorg4j.domain.custom;

import com.schemaorg4j.domain.Action;
import com.schemaorg4j.domain.CreativeWork;
import com.schemaorg4j.domain.ImageObject;
import com.schemaorg4j.domain.Thing;
import com.schemaorg4j.domain.combo.CreativeWorkOrEvent;
import com.schemaorg4j.domain.combo.PropertyValueOrTextOrURL;
import com.schemaorg4j.util.OrText;

public class SimpleThing implements Thing {

    private String simpleValue;
    private Thing nextThing;

    @Override
    public String getAdditionalType() {
        return null;
    }

    @Override
    public void setAdditionalType(String additionalType) {

    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void setName(String name) {

    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public void setDescription(String description) {

    }

    @Override
    public CreativeWorkOrEvent getSubjectOf() {
        return null;
    }

    @Override
    public void setSubjectOf(CreativeWorkOrEvent subjectOf) {

    }

    @Override
    public String getAlternateName() {
        return null;
    }

    @Override
    public void setAlternateName(String alternateName) {

    }

    @Override
    public String getSameAs() {
        return null;
    }

    @Override
    public void setSameAs(String sameAs) {

    }

    @Override
    public Action getPotentialAction() {
        return null;
    }

    @Override
    public void setPotentialAction(Action potentialAction) {

    }

    @Override
    public String getDisambiguatingDescription() {
        return null;
    }

    @Override
    public void setDisambiguatingDescription(String disambiguatingDescription) {

    }

    @Override
    public OrText<ImageObject> getImage() {
        return null;
    }

    @Override
    public void setImage(OrText<ImageObject> image) {

    }

    @Override
    public PropertyValueOrTextOrURL getIdentifier() {
        return null;
    }

    @Override
    public void setIdentifier(PropertyValueOrTextOrURL identifier) {

    }

    @Override
    public OrText<CreativeWork> getMainEntityOfPage() {
        return null;
    }

    @Override
    public void setMainEntityOfPage(OrText<CreativeWork> mainEntityOfPage) {

    }

    @Override
    public String getUrl() {
        return null;
    }

    @Override
    public void setUrl(String url) {

    }

    @Override
    public com.schemaorg4j.domain.error.SchemaOrg4JError getSchemaOrg4JError() {
        return null;
    }

    @Override
    public void setSchemaOrg4JError(
        com.schemaorg4j.domain.error.SchemaOrg4JError schemaOrg4JError) {

    }

    @Override
    public String getSimpleValue() {
        return simpleValue;
    }

    @Override
    public void setSimpleValue(String simpleValue) {
        this.simpleValue = simpleValue;
    }

    @Override
    public com.schemaorg4j.domain.datatypes.SchemaOrg4JAdditionalData getSchemaOrg4JAdditionalData() {
        return null;
    }

    @Override
    public void setSchemaOrg4JAdditionalData(
        com.schemaorg4j.domain.datatypes.SchemaOrg4JAdditionalData schemaOrg4JAdditionalData) {

    }

    @Override
    public Thing getNextThing() {
        return nextThing;
    }

    @Override
    public void setNextThing(Thing nextThing) {
        this.nextThing = nextThing;
    }

    @Override
    public boolean isSimpleThing() {
        return true;
    }
}
