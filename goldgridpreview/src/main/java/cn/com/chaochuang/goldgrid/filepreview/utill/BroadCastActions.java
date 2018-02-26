package cn.com.chaochuang.goldgrid.filepreview.utill;

/**
 * Created by lyy on 2017/5/15.
 * 广播信息类
 */

public class BroadCastActions {
    //测试广播
    public static final String testBroadcastAction = "BC.testaction";
    //获取软件版本号成功
    public static final String BC_GetVerSionSuccess = "BC_GetVerSionSuccess";
    //获取软件版本号失败
    public static final String BC_GetVerSionFailure = "BC_GetVerSionFailure";
    //登录成功
    public static final String BC_LoginSuccess ="BC_LoginSuccess";
    //登录失败
    public static final String BC_LoginFailure = "BC_LoginFailure";

    //退出系统成功
    public static final String BC_ExitSuccess = "BC_ExitSuccess";
    //退出系统失败
    public static final String BC_ExitFailure = "BC_ExitFailure";

    //获取通知公告列表成功
    public static final String BC_GetNoticeListSuccess = "BC_GetNoticeListSuccess";
    //获取通知公告列表失败
    public static final String BC_GetNoticeListFailure = "BC_GetNoticeListFailure";
    //通过ID查找通知公告信息成功
    public static final String BC_findNoticeByIdSuccess ="BC_findNoticeByIdSuccess";
    //通过ID查找通知公告信息失败
    public static final String BC_findNoticeByIdFailure ="BC_findNoticeByIdFailure";

    //查询当前会议列表（现在正在召开的会议列表）成功
    public static final String BC_queryNowMeetingSuccess ="BC_queryNowMeetingSuccess";
    //查询当前会议列表（现在正在召开的会议列表）失败
    public static final String BC_queryNowMeetingFailure ="BC_queryNowMeetingFailure";
    //通过ID查找会议详细信息成功
    public static final String BC_findMeetingDetailByIdSuccess ="BC_findMeetingDetailByIdSuccess";
    //通过ID查找会议详细信息失败
    public static final String BC_findMeetingDetailByIdFailure ="BC_findMeetingDetailByIdFailure";
    //查询会议列表（所有会议）成功
    public static final String BC_queryAllMeetingSuccess ="BC_findMeetingDetailByIdSuccess";
    //查询会议列表（所有会议）失败
    public static final String BC_queryAllMeetingFailure ="BC_findMeetingDetailByIdFailure";
    //获取会议类型列表成功
    public static final String BC_GetMeetingTypeSuccess = "BC_GetMeetingTypeSuccess";
    //获取会议类型列表失败
    public static final String BC_GetMeetingTypeFailure = "BC_GetMeetingTypeSuccess";

    //获取资料库列表成功
    public static final String BC_GetDataBankSuccess = "BC_GetDataBankSuccess";
    //获取资料库列表失败
    public static final String BC_GetDataBankFailure = "BC_GetDataBankFailure";

    //下载文件成功（通知公告，资料库，我的会议）
    public static final String BC_DownFileSuccess = "BC_DownFileSuccess";
    //失败
    public static final String BC_DownFileFailure= "BC_DownFileFailure";
    //删除批注成功
    public static final String BC_DelAnnotSuccess = "BC_DelAnnotSuccess";
    //删除批注失败
    public static final String BC_DelAnnotFailure = "BC_DelAnnotFailure";
    //获取批注成功
    public static final String BC_GetAllAnnotSuccess = "BC_GetAllAnnotSuccess";
    //获取批注失败
    public static final String BC_GetAllAnnotFailure = "BC_GetAllAnnotFailure";
    //添加批注成功
    public static final String BC_AddAnnotSuccess = "BC_AddAnnotSuccess";
    //添加批注失败
    public static final String BC_AddAnnotFailure = "BC_AddAnnotFailure";
    //修改文字批注成功
    public static final String BC_UpdateTextAnnotContentSuccess = "BC_UpdateTextAnnotContentSuccess";
    //修改文字批注失败
    public static final String BC_UpdateTextAnnotContentFailure = "BC_UpdateTextAnnotContentFailure";
    //发送批注成功
    public static final String BC_SendAnnotSuccess = "BC_SendAnnotSuccess";
    //发送批注失败
    public static final String BC_SendAnnotFailure = "BC_SendAnnotFailure";
    //刷新当前会议数量
    public static final String BC_refreshNowMeetingNumber = "BC_refreshNowMeetingNumber";
    //刷新我的会议数量
    public static final String BC_refreshMyMeetingNumber = "BC_refreshMyMeetingNumber";
    //刷新通知公告数量
    public static final String BC_refreshNoticeNumber = "BC_refreshNoticeNumber";
    //刷新资料库数量
    public static final String BC_refreshDataBankNumber = "BC_refreshDataBankNumber";

}
