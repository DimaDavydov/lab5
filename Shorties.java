package ru.dima.model;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public abstract class Shorties implements Information {
    protected String shortyName;
    protected Positions.QueuePosition shortyPosition;
    protected Integer age;

    public Shorties(String ShortyName, Positions.QueuePosition ShortyPosition, int Age) {
        if (ShortyName.equals("")) {
            throw new EmptyStringException("Имя не введено");
        }
        this.shortyName = ShortyName;
        this.shortyPosition = ShortyPosition;
        this.age = Age;
    }

    public String getShortyName() {
        return shortyName;
    }

    public Positions.QueuePosition getShortyPosition() {
        return shortyPosition;
    }

    public Integer getAge() throws AgeException {
        if (age < 0) {
            throw new AgeException("Недопустимо отрицательное значение возраста");
        }
        return age;
    }

    public void ChangePosition(Positions.QueuePosition position) {
        this.shortyPosition = position;
    }

    @Override
    public void getInformation() {
        System.out.println(getShortyName() + " в " + getShortyPosition() + " вереницы малышей");
    }

    @Override public String toString() {
        return "Shorties{" +
            "shortyName='" + shortyName + '\'' +
            ", shortyPosition=" + shortyPosition +
            ", age=" + age +
            '}';
    }
}