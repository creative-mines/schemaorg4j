package com.schemaorg4j.domain.custom;

import com.schemaorg4j.domain.Action;
import com.schemaorg4j.domain.Thing;
import com.schemaorg4j.domain.combo.CreativeWorkOrEvent;
import com.schemaorg4j.domain.combo.CreativeWorkOrURL;
import com.schemaorg4j.domain.combo.ImageObjectOrURL;
import com.schemaorg4j.domain.combo.PropertyValueOrTextOrURL;
import com.schemaorg4j.domain.datatypes.SchemaOrg4JAdditionalData;
import com.schemaorg4j.domain.datatypes.Text;
import com.schemaorg4j.domain.datatypes.URL;
import com.schemaorg4j.domain.error.SchemaOrg4JError;

public class SimpleThing implements Thing {

    private Thing nextThing;
    private String simpleValue;

    @Override
    public URL getAdditionalTypeData() {
        return null;
    }

    @Override
    public String getAdditionalType() {
        return null;
    }

    @Override
    public void setAdditionalTypeData(URL additionalType) {

    }

    @Override
    public void setAdditionalType(String additionalType) {

    }

    @Override
    public Text getNameData() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void setNameData(Text name) {

    }

    @Override
    public void setName(String name) {

    }

    @Override
    public Text getDescriptionData() {
        return null;
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public void setDescriptionData(Text description) {

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
    public Text getAlternateNameData() {
        return null;
    }

    @Override
    public String getAlternateName() {
        return null;
    }

    @Override
    public void setAlternateNameData(Text alternateName) {

    }

    @Override
    public void setAlternateName(String alternateName) {

    }

    @Override
    public URL getSameAsData() {
        return null;
    }

    @Override
    public String getSameAs() {
        return null;
    }

    @Override
    public void setSameAsData(URL sameAs) {

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
    public Text getDisambiguatingDescriptionData() {
        return null;
    }

    @Override
    public String getDisambiguatingDescription() {
        return null;
    }

    @Override
    public void setDisambiguatingDescriptionData(Text disambiguatingDescription) {

    }

    @Override
    public void setDisambiguatingDescription(String disambiguatingDescription) {

    }

    @Override
    public ImageObjectOrURL getImage() {
        return null;
    }

    @Override
    public void setImage(ImageObjectOrURL image) {

    }

    @Override
    public PropertyValueOrTextOrURL getIdentifier() {
        return null;
    }

    @Override
    public void setIdentifier(PropertyValueOrTextOrURL identifier) {

    }

    @Override
    public CreativeWorkOrURL getMainEntityOfPage() {
        return null;
    }

    @Override
    public void setMainEntityOfPage(CreativeWorkOrURL mainEntityOfPage) {

    }

    @Override
    public URL getUrlData() {
        return null;
    }

    @Override
    public String getUrl() {
        return null;
    }

    @Override
    public void setUrlData(URL url) {

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
}
