package ar.com.utn.form;

/**
 * Created by julian on 10/08/17.
 */
public class SelectorForm {

    private Long value;
    private String label;

    public SelectorForm(Long value, String label) {
        this.value = value;
        this.label = label;
    }


    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
