
package sigmu.simpleinfer.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PatchOption {

    @SerializedName("patch")
    @Expose
    private List<Patch> patch = null;
    @SerializedName("patch_id")
    @Expose
    private Integer patchId;

    /**
     * No args constructor for use in serialization
     * 
     */
    public PatchOption() {
    }

    /**
     * 
     * @param patch
     * @param patchId
     */
    public PatchOption(List<Patch> patch, Integer patchId) {
        super();
        this.patch = patch;
        this.patchId = patchId;
    }

    public List<Patch> getPatch() {
        return patch;
    }

    public void setPatch(List<Patch> patch) {
        this.patch = patch;
    }

    public Integer getPatchId() {
        return patchId;
    }

    public void setPatchId(Integer patchId) {
        this.patchId = patchId;
    }

}
