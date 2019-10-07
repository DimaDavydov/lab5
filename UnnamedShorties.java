package ru.dima.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString(callSuper = true)
public class UnnamedShorties extends Shorties implements ActionsOfUnnamedShorties, Comparable<UnnamedShorties> {
    private Integer weight;

    public UnnamedShorties(String name, Positions.QueuePosition position, int age) {
        super(name, position, age);
    }

    @Override
    public void abut() {
        System.out.println(getShortyName() + " упираются стальными остриями альпенштоков в лед");
    }

    @Override
    public void peg() {
        System.out.println(getShortyName() + " привязывают Знайку позади всех");
    }

    @Override
    public void notAllow() {
        System.out.println(getShortyName() + " не разрешают Знайке вылезать вперёд");
        tone.getTone(1);
    }

    @Override
    public void cutDown() {
        System.out.println(getShortyName() + " вырубают во льду ступеньки");
    }

    @Override
    public void stand() {
        System.out.println(getShortyName() + " стоят на ледяных ступеньках");
        tone.getTone(2);
    }

    @Override
    public void loosen() {
        System.out.println(getShortyName() + " постепенно отпускают верёвку");
    }

    @Override
    public void watch() {
        System.out.println(getShortyName() + " тщательно следят, чтобы верёвка не выскользнула из рук");
    }

    @Override
    public void getInformation() {
        System.out.println(getShortyName() + " в " + getShortyPosition() + " вереницы малышей");
    }

    public static class tone {
        public static void getTone(int a) {
            class tone1 {
                void show() {
                    System.out.println("вежливо");
                }
            }
            class tone2 {
                void show() {
                    System.out.println("в напряжении");
                }
            }
            if (a == 1) {
                tone1 t1 = new tone1();
                t1.show();
            }
            if (a == 2) {
                tone2 t2 = new tone2();
                t2.show();
            }
        }
    }

    @Override
    public int hashCode() {
        int hc;
        hc = 5 + 8 * age;
        hc = 7 + hc * shortyName.length() * 3;
        return hc;
    }

    @Override
    public int compareTo(UnnamedShorties unnamedShorties) {
        return Integer.compare(weight, unnamedShorties.weight);
    }

    @Override public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UnnamedShorties)) {
            return false;
        }
        UnnamedShorties that = (UnnamedShorties) o;
        return getWeight().equals(that.getWeight());
    }
}