
package sigmu.simpleinfer.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Patches {

    @SerializedName("patch_options")
    @Expose
    private List<PatchOption> patchOptions = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Patches() {
    }

    /**
     * 
     * @param patchOptions
     */
    public Patches(List<PatchOption> patchOptions) {
        super();
        this.patchOptions = patchOptions;
    }

    public List<PatchOption> getPatchOptions() {
        return patchOptions;
    }

    public void setPatchOptions(List<PatchOption> patchOptions) {
        this.patchOptions = patchOptions;
    }

}
