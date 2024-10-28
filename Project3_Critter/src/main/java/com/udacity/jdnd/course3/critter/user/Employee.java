package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.schedule.Schedule;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;

@Entity
public class Employee extends Users {

    @Column
    @ElementCollection(targetClass = EmployeeSkill.class)
    private Set<EmployeeSkill> skills;

    @Column
    @ElementCollection(targetClass = DayOfWeek.class)
    private Set<DayOfWeek> daysAvailable;

    @ManyToMany(mappedBy = "employees")
    private List<Schedule> schedules;

    public Employee(long id, String name, Set<EmployeeSkill> skills, Set<DayOfWeek> daysAvailable) {
        super();
        this.skills = skills;
        this.daysAvailable = daysAvailable;
    }

    public Employee() {
    }

    public Set<EmployeeSkill> getSkills() {
        return skills;
    }

    public void setSkills(Set<EmployeeSkill> skills) {
        this.skills = skills;
    }

    public Set<DayOfWeek> getDaysAvailable() {
        return daysAvailable;
    }

    public void setDaysAvailable(Set<DayOfWeek> daysAvailable) {
        this.daysAvailable = daysAvailable;
    }

    public List<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<Schedule> schedules) {
        this.schedules = schedules;
    }

    public Employee(Set<EmployeeSkill> skills, Set<DayOfWeek> daysAvailable, List<Schedule> schedules) {
        super();
        this.skills = skills;
        this.daysAvailable = daysAvailable;
        this.schedules = schedules;
    }
}
