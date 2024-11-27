package lk.ijse.ormcoursework.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "programs")
public class Programs  {
    @Id
    @Column(name = "program_id")
    private String id;

    @Column(name = "program_name")
    private String name;

    @Column(name = "duration")
    private String duration;

    @Column(name = "fee")
    private String fee;

    public Programs() {
    }

    public Programs(String id, String name, String duration, String fee) {
        this.id = id;
        this.name = name;
        this.duration = duration;
        this.fee = fee;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    @Override
    public String toString() {
        return "Programs{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", duration='" + duration + '\'' +
                ", fee=" + fee +
                '}';
    }
}
