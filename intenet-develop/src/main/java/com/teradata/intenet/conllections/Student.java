package com.teradata.intenet.conllections;

/**
 * @Project:
 * @Description:
 * @Version 1.0.0
 * @Throws SystemException:
 * @Author: <li>2018/12/4/004 Administrator Create 1.0
 * @Copyright ©2018-2019 al.github
 * @Modified By:
 */
public class Student implements Comparable<Student>{
    private String name;
    private int score;

    public Student(String name,int score){
        this.name=name;
        this.score=score;
    }

    @Override
    public int compareTo(Student o) {
        if (o.score<this.score){
            return 1;
        }else if(o.score>this.score){
            return -1;
        }
        return 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
