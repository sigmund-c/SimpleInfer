
package sigmu.simpleinfer.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InferBugFix {

    @SerializedName("access")
    @Expose
    private String access;
    @SerializedName("bug_trace")
    @Expose
    private List<BugTrace> bugTrace = null;
    @SerializedName("bug_type")
    @Expose
    private String bugType;
    @SerializedName("bug_type_hum")
    @Expose
    private String bugTypeHum;
    @SerializedName("column")
    @Expose
    private Integer column;
    @SerializedName("file")
    @Expose
    private String file;
    @SerializedName("hash")
    @Expose
    private String hash;
    @SerializedName("key")
    @Expose
    private String key;
    @SerializedName("line")
    @Expose
    private Integer line;
    @SerializedName("patch_choice")
    @Expose
    private Integer patchChoice;
    @SerializedName("patches")
    @Expose
    private Patches patches;
    @SerializedName("procedure")
    @Expose
    private String procedure;
    @SerializedName("procedure_start_line")
    @Expose
    private Integer procedureStartLine;
    @SerializedName("qualifier")
    @Expose
    private String qualifier;
    @SerializedName("severity")
    @Expose
    private String severity;
    @SerializedName("snapshot1")
    @Expose
    private String snapshot1;
    @SerializedName("snapshot2")
    @Expose
    private String snapshot2;

    /**
     * No args constructor for use in serialization
     * 
     */
    public InferBugFix() {
    }

    /**
     * 
     * @param severity
     * @param access
     * @param procedureStartLine
     * @param line
     * @param patches
     * @param column
     * @param patchChoice
     * @param procedure
     * @param bugTypeHum
     * @param snapshot2
     * @param snapshot1
     * @param bugType
     * @param file
     * @param bugTrace
     * @param qualifier
     * @param hash
     * @param key
     */
    public InferBugFix(String access, List<BugTrace> bugTrace, String bugType, String bugTypeHum, Integer column, String file, String hash, String key, Integer line, Integer patchChoice, Patches patches, String procedure, Integer procedureStartLine, String qualifier, String severity, String snapshot1, String snapshot2) {
        super();
        this.access = access;
        this.bugTrace = bugTrace;
        this.bugType = bugType;
        this.bugTypeHum = bugTypeHum;
        this.column = column;
        this.file = file;
        this.hash = hash;
        this.key = key;
        this.line = line;
        this.patchChoice = patchChoice;
        this.patches = patches;
        this.procedure = procedure;
        this.procedureStartLine = procedureStartLine;
        this.qualifier = qualifier;
        this.severity = severity;
        this.snapshot1 = snapshot1;
        this.snapshot2 = snapshot2;
    }

    public String getAccess() {
        return access;
    }

    public void setAccess(String access) {
        this.access = access;
    }

    public List<BugTrace> getBugTrace() {
        return bugTrace;
    }

    public void setBugTrace(List<BugTrace> bugTrace) {
        this.bugTrace = bugTrace;
    }

    public String getBugType() {
        return bugType;
    }

    public void setBugType(String bugType) {
        this.bugType = bugType;
    }

    public String getBugTypeHum() {
        return bugTypeHum;
    }

    public void setBugTypeHum(String bugTypeHum) {
        this.bugTypeHum = bugTypeHum;
    }

    public Integer getColumn() {
        return column;
    }

    public void setColumn(Integer column) {
        this.column = column;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Integer getLine() {
        return line;
    }

    public void setLine(Integer line) {
        this.line = line;
    }

    public Integer getPatchChoice() {
        return patchChoice;
    }

    public void setPatchChoice(Integer patchChoice) {
        this.patchChoice = patchChoice;
    }

    public Patches getPatches() {
        return patches;
    }

    public void setPatches(Patches patches) {
        this.patches = patches;
    }

    public String getProcedure() {
        return procedure;
    }

    public void setProcedure(String procedure) {
        this.procedure = procedure;
    }

    public Integer getProcedureStartLine() {
        return procedureStartLine;
    }

    public void setProcedureStartLine(Integer procedureStartLine) {
        this.procedureStartLine = procedureStartLine;
    }

    public String getQualifier() {
        return qualifier;
    }

    public void setQualifier(String qualifier) {
        this.qualifier = qualifier;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getSnapshot1() {
        return snapshot1;
    }

    public void setSnapshot1(String snapshot1) {
        this.snapshot1 = snapshot1;
    }

    public String getSnapshot2() {
        return snapshot2;
    }

    public void setSnapshot2(String snapshot2) {
        this.snapshot2 = snapshot2;
    }

}
