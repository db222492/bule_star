package com.xinzeyijia.houselocks.model.bean

/**
 * @ProjectName: bluetooth_locks
 * @Package: com.xinzeyijia.houselocks.model.bean
 * @ClassName: FaceBean
 * @Description: java类作用描述
 * @Author: 作者名
 * @CreateDate: 2019/12/14 下午 05:31
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/12/14 下午 05:31
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
class FaceBean(

/**
     * refresh_token : 25.b55fe1d287227ca97aab219bb249b8ab.315360000.1798284651.282335-8574074
     * expires_in : 2592000
     * scope : public wise_adapt
     * session_key : 9mzdDZXu3dENdFZQurfg0Vz8slgSgvvOAUebNFzyzcpQ5EnbxbF+hfG9DQkpUVQdh4p6HbQcAiz5RmuBAja1JJGgIdJI
     * access_token : 24.6c5e1ff107f0e8bcef8c46d3424a0e78.2592000.1485516651.282335-8574074
     * session_secret : dfac94a3489fe9fca7c3221cbf7525ff
     */

    var refresh_token: String = "",
    var score: String = "",
    var expires_in: Int = -1,
    var error_code: Int = -1,
    var scope: String = "",
    var session_key: String = "",
    var access_token: String = "",
    var session_secret: String = "",
    var face_token: String = "",
    var error_msg: String = "",
    var log_id: String = "",
    var timestamp: String = "",
    var cached: String = "",
    var words_result: FaceParm = FaceParm(),//身份证头像
    var photo: String = "",//身份证头像
    var result:  FaceItem= FaceItem()
)
data class CharacterBean(
    var log_id: Long? = 0,
    var paragraphs_result: List<ParagraphsResult?>? = listOf(),
    var paragraphs_result_num: Int? = 0,
    var words_result: List<WordsResult?>? = listOf(),
    var words_result_num: Int? = 0
)

data class WordsResult(
    var probability: Probability? = Probability(),
    var words: String? = ""
)

data class Probability(
    var average: Double? = 0.0,
    var min: Double? = 0.0,
    var variance: Double? = 0.0
)

data class ParagraphsResult(
    var words_result_idx: List<Int?>? = listOf()
)
class FaceParm(
    var 住址: FaceItem = FaceItem(),
    var 出生: FaceItem = FaceItem(),
    var 姓名: FaceItem = FaceItem(),
    var 公民身份号码: FaceItem = FaceItem(),
    var 性别: FaceItem = FaceItem(),
    var 民族: FaceItem = FaceItem(),
    var image_type: String = "",
    var face_type: String = "",
    var quality_control: String = "",
    var liveness_control: String = ""
)
class FaceItem(
    var words: String = "",
    var face_list: MutableList<FaceBean> = mutableListOf()

)
