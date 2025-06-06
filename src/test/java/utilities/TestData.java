package utilities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;

import java.util.Collections;
import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public class TestData {
    private String idType;
    private String registrantTag;
    private String gender;

    private List<String> email;
    private List<String> password;
    private String groupType;
    private String groupMembershipKind;
    private String region;
    public String groupName;
    private String tags;

    public String familyName;
    public  String givenName;
    public String additionalName;
    private String individualEmail;
    private String dob;
    private String civilStatus;
    private String occupation;
    private String income;
    private String address;
    private String genderType;

    public String getGroupUpdated() {
        return groupUpdated;
    }

    public void setGroupUpdated(String groupUpdated) {
        this.groupUpdated = groupUpdated;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    private String groupUpdated;
    private String group;

    public String getGenderType() {
        return genderType;
    }

    public void setGenderType(String genderType) {
        this.genderType = genderType;
    }

    public String getGenderTypeUpdated() {
        return genderTypeUpdated;
    }

    public void setGenderTypeUpdated(String genderTypeUpdated) {
        this.genderTypeUpdated = genderTypeUpdated;
    }

    private  String genderTypeUpdated;

    public String getDistrictUpdated() {
        return districtUpdated;
    }

    public void setDistrictUpdated(String districtUpdated) {
        this.districtUpdated = districtUpdated;
    }

    private String districtUpdated;

    private String relationName;

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    private String district;
    public String getRelationNameUpdated() {
        return relationNameUpdated;
    }

    public void setRelationNameUpdated(String relationNameUpdated) {
        this.relationNameUpdated = relationNameUpdated;
    }

    private String relationNameUpdated;

    public String getRelationName() {
        return relationName;
    }

    public void setRelationName(String relationName) {
        this.relationName = relationName;
    }

    public String getRelationInverseName() {
        return relationInverseName;
    }

    public void setRelationInverseName(String relationInverseName) {
        this.relationInverseName = relationInverseName;
    }

    private String relationInverseName;

    public String getRegistrantTagUpdated() {
        return registrantTagUpdated;
    }

    public void setRegistrantTagUpdated(String registrantTagUpdated) {
        this.registrantTagUpdated = registrantTagUpdated;
    }

    private String registrantTagUpdated;

    public String getRegionUpdated() {
        return regionUpdated;
    }

    public void setRegionUpdated(String regionUpdated) {
        this.regionUpdated = regionUpdated;
    }

    private String regionUpdated;

    public String getGivenNameUpdated() {
        return givenNameUpdated;
    }

    public void setGivenNameUpdated(String givenNameUpdated) {
        this.givenNameUpdated = givenNameUpdated;
    }

    private String givenNameUpdated;


    public String getIdTypeUpdated() {
        return idTypeUpdated;
    }

    public void setIdTypeUpdated(String idTypeUpdated) {
        this.idTypeUpdated = idTypeUpdated;
    }

    private String idTypeUpdated;

    public String getGroupNameUpdated() {
        return groupNameUpdated;
    }

    public void setGroupNameUpdated(String groupNameUpdated) {
        this.groupNameUpdated = groupNameUpdated;
    }

    private String groupNameUpdated;

    public String getGroupTypeUpdated() {
        return groupTypeUpdated;
    }

    public void setGroupTypeUpdated(String groupTypeUpdated) {
        this.groupTypeUpdated = groupTypeUpdated;
    }

    private String groupTypeUpdated;

    public String getGroupMembershipKindUpdated() {
        return groupMembershipKindUpdated;
    }

    public void setGroupMembershipKindUpdated(String groupMembershipKindUpdated) {
        this.groupMembershipKindUpdated = groupMembershipKindUpdated;
    }

    private String groupMembershipKindUpdated;

    public String getGenderUpdated() {
        return genderUpdated;
    }

    public void setGenderUpdated(String genderUpdated) {
        this.genderUpdated = genderUpdated;
    }

    private String genderUpdated;


    public String getRegion() {

        return region;
    }

    public void setRegion(String region) {

        this.region = region;
    }

    public String getGroupMembershipKind() {

        return groupMembershipKind;
    }

    public void setGroupMembershipKind(String groupMembershipKind) {

        this.groupMembershipKind = groupMembershipKind;
    }

    public String getGroupType() {

        return groupType;
    }

    public void setGroupType(String groupType) {

        this.groupType = groupType;
    }

    public String getPassword() {
        return password.toString();}

    public void setPassword(String password) {

        this.password = Collections.singletonList(password);
    }

    public List<String> getEmail() {
        return email;
    }

    public void setEmail(List<String> email) {

        this.email = email;
    }

    public String getIdType() {

        return idType;
    }

    public void setIdType(String idType) {

        this.idType = idType;
    }

    public String getRegistrantTag() {

        return registrantTag;
    }

    public void setRegistrantTag(String registrantTag) {

        this.registrantTag = registrantTag;
    }

    public String getGender() {

        return gender;
    }

    public void setGender(String gender) {

        this.gender = gender;
    }


    public String getGroupName() {
        return groupName;
    }

    public void setGroupname(String groupName) {
        this.groupName = groupName;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getAddress() {
        return address;
    }

    public String getFamilyName() {
        return familyName;
    }
    public String getGivenName() {
        return givenName;
    }

    public String getAdditionalName() {
        return additionalName;
    }

    public String getDOB() {
        return dob;
    }

    public String getIndividualEmail() {
        return individualEmail;
    }
    public String getCivilStatus(){
        return civilStatus;
    }

    public String getOccupation() {
        return occupation;
    }
    public String getIncome(){
        return income;
    }
}