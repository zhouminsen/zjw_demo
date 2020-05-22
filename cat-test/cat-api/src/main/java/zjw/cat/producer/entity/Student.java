package zjw.cat.producer.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Administrator
 */
@Data
public class Student implements Serializable {


    private static final long serialVersionUID = -8098570595898776365L;
    /**
     * 主键
     */
    private Integer id;

    /**
     * 库存
     */
    private String className;

    /**
     * 名称
     */
    private Integer score;

}