
package sigmu.simpleinfer.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BugTrace {

    @SerializedName("column_number")
    @Expose
    private Integer columnNumber;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("filename")
    @Expose
    private String filename;
    @SerializedName("level")
    @Expose
    private Integer level;
    @SerializedName("line_number")
    @Expose
    private Integer lineNumber;

    /**
     * No args constructor for use in serialization
     * 
     */
    public BugTrace() {
    }

    /**
     * 
     * @param filename
     * @param columnNumber
     * @param level
     * @param description
     * @param lineNumber
     */
    public BugTrace(Integer columnNumber, String description, String filename, Integer level, Integer lineNumber) {
        super();
        this.columnNumber = columnNumber;
        this.description = description;
        this.filename = filename;
        this.level = level;
        this.lineNumber = lineNumber;
    }

    public Integer getColumnNumber() {
        return columnNumber;
    }

    public void setColumnNumber(Integer columnNumber) {
        this.columnNumber = columnNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(Integer lineNumber) {
        this.lineNumber = lineNumber;
    }

}
