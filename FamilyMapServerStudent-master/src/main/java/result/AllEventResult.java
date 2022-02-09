package result;

import model.Event;

public class AllEventResult {

    private Event[] data;
    private boolean success;

    public AllEventResult(Event[] data, boolean success) {
        this.data = data;
        this.success = success;
    }

    public Event[] getData() {
        return data;
    }

    public void setData(Event[] data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
