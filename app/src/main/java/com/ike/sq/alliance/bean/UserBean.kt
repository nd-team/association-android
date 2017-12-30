package com.ike.sq.alliance.bean

import org.greenrobot.greendao.annotation.Entity
import org.greenrobot.greendao.annotation.Id
import org.greenrobot.greendao.annotation.Property
import java.io.Serializable

/**
 * Created by T-BayMax on 2017/8/28.
 */
@Entity
class UserBean(
        final val  serialVersionUID :Long= 42L,
        @Id
        var id: String,
        @Property(nameInDb = "account")
        var account: String,
        @Property(nameInDb = "password")
        var password: String,
        @Property(nameInDb = "username")
        var username: String, //用户名
        @Property(nameInDb = "nickname")
        var nickname: String, //昵称
        @Property(nameInDb = "headPath")
        var headPath: String, //头像
        @Property(nameInDb = "sexType")
        var sexType: String, //性别 { S:删除 }
        @Property(nameInDb = "phone")
        var phone: String, //手机号
        @Property(nameInDb = "realName")
        var realName: String, //真实姓名
        @Property(nameInDb = "telephone")
        var telephone: String, //联系号码
        @Property(nameInDb = "interest")
        var interest: String, //兴趣
        @Property(nameInDb = "address")
        var address: String, //地址
        @Property(nameInDb = "birthday")
        var birthday: String, //生日
        @Property(nameInDb = "disposition")
        var disposition: String, //性格
        @Property(nameInDb = "userId")
        var nativePlace: String, //籍贯
        @Property(nameInDb = "school")
        var school: String, //毕业学校
        @Property(nameInDb = "education")
        var education: String, //学历
        @Property(nameInDb = "company")
        var company: String, //公司
        @Property(nameInDb = "job")
        var job: String, //职位
        @Property(nameInDb = "fatherName")
        var fatherName: String, //父亲姓名
        @Property(nameInDb = "motherName")
        var motherName: String, //母亲姓名
        @Property(nameInDb = "marriage")
        var marriage: Boolean, //婚姻状况
        @Property(nameInDb = "qq")
        var qq: String, //qq
        @Property(nameInDb = "email")
        var email: String, //邮箱
        @Property(nameInDb = "weChat")
        var weChat: String, //微信
        @Property(nameInDb = "number")
        var number: String

) : Serializable {
}