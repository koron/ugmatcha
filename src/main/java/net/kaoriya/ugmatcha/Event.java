package net.kaoriya.ugmatcha;

class Event {

    int id;

    int nextId;

    int index;

    boolean last;

    Event(int id, int nextId, int index, boolean last) {
        this.id = id;
        this.nextId = nextId;
        this.index = index;
        this.last = last;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("Event{")
            .append("id=").append(this.id)
            .append(" nextId=").append(this.nextId)
            .append(" index=").append(this.index)
            .append(" last=").append(this.last)
            .append("}");
        return s.toString();
    }
}
