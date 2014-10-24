package com.test.utils;

import com.test.utils.NetworkAction;

;
public class ErrorMsg {

	/**
	 * 根据输入的数据操作类型获取相应的错误信息
	 * 
	 * @param request
	 *            向服务器发送的数据错误类型
	 * @param code
	 *            返回的错误代码
	 * @return
	 */
	public static String getErrorMsg(NetworkAction request, int code) {
		String result = null;
		if (request.equals(NetworkAction.验证验证码)) {
			switch (code) {
			case 0:
				result = "提交信息不全";
				break;

			case 1:
				result = "验证成功";
				break;
			case 2:
				result = "验证码错误";
				break;
			default:
				break;
			}
		} else if (request.equals(NetworkAction.用户注册)) {
			switch (code) {
			case 2:
				result = "用户名为20字内，不能为空";
				break;

			case 3:
				result = "密码为空 或 长度不在6到32位字符之间";
				break;
			case 4:
				result = "两次输入密码不一致";
				break;
			case 5:
				result = "邮箱为空 或 格式不合法";
				break;
			case 6:
				result = "用户名已经存在";
				break;
			case 7:
				result = "失败";
				break;
			case 8:
				result = "店铺ID错误";
				break;
			case 9:
				result = "该手机号码已被占用";
				break;
			case 10:
				result = "短信验证失败";
				break;
			default:
				break;
			}
		} else if (request.equals(NetworkAction.我的消息)) {
			switch (code) {
			case 3:
				result = "未登录";
				break;
			case 6:
				result = "数据库错误";
				break;
			}
		} else if (request.equals(NetworkAction.登录)) {
			switch (code) {
			case 2:
				result = "用户名为空";
				break;
			case 3:
				result = "密码为空";
				break;
			case 4:
				result = "用户名输入错误或不存在";
				break;
			case 5:
				result = "密码错误";
				break;
			case 6:
				result = "数据库错误";
				break;
			}
		} else if (request.equals(NetworkAction.找回密码)) {
			switch (code) {
			case 2:
				result = "操作失败";
				break;
			case 3:
				result = "用户名和手机号码不匹配";
				break;
			}
		}
		else if (request.equals(NetworkAction.修改密码)) {
			switch (code) {
			case 2:
				result = "原密码错误";
				break;
			case 3:
				result = "未登录";
				break;
			case 4:
				result = "系统繁忙，请稍后再试";
				break;
			}
		}
		else if (request.equals(NetworkAction.新增收货地址)) {
			switch (code) {
			case 2:
				result = "未登录";
				break;
			case 3:
				result = "提交参数错误";
				break;
			case 4:
				result = "最多创建10个地址";
				break;
			}
		}
		else if (request.equals(NetworkAction.获取收货地址列表)) {
			switch (code) {
			case 2:
				result = "未登录";
				break;
			}
		}
		else if (request.equals(NetworkAction.设置默认地址)) {
			switch (code) {
			case 2:
				result = "未登录";
				break;
			}
		}
		else if (request.equals(NetworkAction.查询购买商品的优惠券)) {
			switch (code) {
			case 3:
				result = "未登录";
				break;
			case 6:
				result = "数据库错误";
				break;
			}
		}
		else if (request.equals(NetworkAction.我的优惠券)) {
			switch (code) {
			case 0:
				result = "操作失败";
				break;
			case 3:
				result = "未登录";
				break;
			case 6:
				result = "数据库错误";
				break;
			}
		}
		else if (request.equals(NetworkAction.保存个人信息)) {
			switch (code) {
			case 0:
				result = "操作失败";
				break;
			case 3:
				result = "未登录";
				break;
			case 2:
				result = "数据库错误";
				break;
			}
		}
		else if (request.equals(NetworkAction.订单详情)) {
			switch (code) {
			case 2:
				result = "未登录";
				break;
			case 3:
				result = "参数错误";
				break;
			case 6:
				result = "数据库错误";
				break;
			}
		}
		else if (request.equals(NetworkAction.取消订单)) {
			switch (code) {
			case 0:
				result = "操作失败";
				break;
			case 2:
				result = "您不能修改该订单（非自己的订单或订单不存在）";
				break;
			case 3:
				result = "未登录";
				break;
			}
		}
		else if (request.equals(NetworkAction.确认收货)) {
			switch (code) {
			case 0:
				result = "操作失败";
				break;
			case 2:
				result = "参数错误";
				break;
			case 3:
				result = "未登录";
				break;
			case 6:
				result = "数据库错误";
				break;
			}
		}
		else if (request.equals(NetworkAction.评论订单)) {
			switch (code) {
			case 0:
				result = "操作失败";
				break;
			case 4:
				result = "您还不能评论该商品(没有购买或没有完成交易)";
				break;
			case 3:
				result = "未登录";
				break;
			case 6:
				result = "数据库错误";
				break;
			}
		}
		else if (request.equals(NetworkAction.意见反馈)) {
			switch (code) {
			case 0:
				result = "操作失败";
				break;
			case 2:
				result = "十分钟内只能请求一次";
				break;
			case 3:
				result = "未登录";
				break;
			}
		}
		else {
			result = "操作失败";
		}
		return result;
	}
}
