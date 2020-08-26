
package sigmu.simpleinfer.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Patch {

    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("kind")
    @Expose
    private String kind;
    @SerializedName("partial_patch")
    @Expose
    private String partialPatch;
    @SerializedName("start")
    @Expose
    private Integer start;
    @SerializedName("stop")
    @Expose
    private Integer stop;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Patch() {
    }

    /**
     * 
     * @param stop
     * @param kind
     * @param partialPatch
     * @param start
     * @param description
     */
    public Patch(String description, String kind, String partialPatch, Integer start, Integer stop) {
        super();
        this.description = description;
        this.kind = kind;
        this.partialPatch = partialPatch;
        this.start = start;
        this.stop = stop;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getPartialPatch() {
        return partialPatch;
    }

    public void setPartialPatch(String partialPatch) {
        this.partialPatch = partialPatch;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getStop() {
        return stop;
    }

    public void setStop(Integer stop) {
        this.stop = stop;
    }

}
