package siister.neo4j.api.common;

/**
 * ContactEnum
 * attribute: 
 *  1. name = siister.tb_m_master
 *  2. code = sisiter.tb_m_master
 *  3. rel (relationship) = neo4j
 */
public enum ContactEnum {
    PHONE       ("01", "has_phone"), 
    EMAIL       ("02", "has_email"), 
    OTHER       ("03", "has"), 
    PASSPORT    ("04", "has_passport"), 
    KTP         ("05", "has_ktp"), 
    ALIAS       ("06", "aka"), 
    LKN         ("07", "has_lkn"),
    BANK        ("08", "has_bank");

    ContactEnum(String code, String rel) {
        this.code = code;
        this.rel = rel;
    }

    private final String code;
    private final String rel;

    public String getCode() { return this.code; }
    public String getRel() { return this.rel; }

    public static ContactEnum fromCodes(String code) {
        for(ContactEnum e : ContactEnum.values()) {
            if(e.getCode().equals(code)) {
                return e;
            }
        }

        return null;
    }
}