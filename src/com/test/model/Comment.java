package com.test.model;

public class Comment {
private String comment_id;//评论ID
private String product_id;//商品ID
private String uid;//用户ID
private String username;//用户昵称
private String comment_subject;//评论主题
private String comment_content;//评论内容
private String comment_star;//评论星级
private String comment_evaluation;//评论等级
private String createtime;//评论时间
private String OutDeadLine;//结束时间
public String getComment_id() {
	return comment_id;
}
public void setComment_id(String comment_id) {
	this.comment_id = comment_id;
}
public String getProduct_id() {
	return product_id;
}
public void setProduct_id(String product_id) {
	this.product_id = product_id;
}
public String getUid() {
	return uid;
}
public void setUid(String uid) {
	this.uid = uid;
}
public String getUsername() {
	return username;
}
public void setUsername(String username) {
	this.username = username;
}
public String getComment_subject() {
	return comment_subject;
}
public void setComment_subject(String comment_subject) {
	this.comment_subject = comment_subject;
}
public String getComment_content() {
	return comment_content;
}
public void setComment_content(String comment_content) {
	this.comment_content = comment_content;
}
public String getComment_star() {
	return comment_star;
}
public void setComment_star(String comment_star) {
	this.comment_star = comment_star;
}
public String getComment_evaluation() {
	return comment_evaluation;
}
public void setComment_evaluation(String comment_evaluation) {
	this.comment_evaluation = comment_evaluation;
}
public String getCreatetime() {
	return createtime;
}
public void setCreatetime(String createtime) {
	this.createtime = createtime;
}
public String getOutDeadLine() {
	return OutDeadLine;
}
public void setOutDeadLine(String outDeadLine) {
	OutDeadLine = outDeadLine;
}


}
