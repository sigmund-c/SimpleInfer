package sigmu.simpleinfer.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InferBugFix{
    @SerializedName("bug_hash")
    @Expose
    private String bugHash;
    @SerializedName("file_name")
    @Expose
    private String fileName;
    @SerializedName("line_start")
    @Expose
    private int lineStart;
    @SerializedName("line_end")
    @Expose
    private int lineEnd;
    @SerializedName("code_snippet")
    @Expose
    private String codeSnippet;

    public InferBugFix() {
    }

    public InferBugFix(String bugHash, String fileName, int lineStart, int lineEnd, String codeSnippet) {
        this.bugHash = bugHash;
        this.fileName = fileName;
        this.lineStart = lineStart;
        this.lineEnd = lineEnd;
        this.codeSnippet = codeSnippet;
    }

    public void setBugHash(String bugHash) {
        this.bugHash = bugHash;
    }

    public String getBugHash() {
        return bugHash;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setLineStart(int lineStart) {
        this.lineStart = lineStart;
    }

    public int getLineStart() {
        return  lineStart;
    }

    public void setLineEnd(int lineEnd) {
        this.lineEnd = lineEnd;
    }

    public int getLineEnd() {
        return lineEnd;
    }

    public void setCodeSnippet(String codeSnippet) {
        this.codeSnippet = codeSnippet;
    }

    public String getCodeSnippet() {
        return codeSnippet;
    }

    @Override
    public String toString() {
        return "Lines " + lineStart + "-" + lineEnd + ": replace with \"" + codeSnippet + "\"";
    }
}
