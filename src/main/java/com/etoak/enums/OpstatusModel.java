package com.etoak.enums;
/**
 *
 * @author zhangcheng
 * 用户组状态枚举类
 * 
 * 操作状态，默认为0---0,正常 1,导入中 2,导入完成，部分成功 3,导入成功 4,导入失败 5,清空中 
 * 6,清空成功 7,清空失败 8,导出中 9,导出停止 10,导出失败 11,导出完成 12,没有号码被导入
 */
public enum OpstatusModel {
	NORMAL(0),IMPORTING(1),IMPORT_PART_SUCC(2),IMPORT_SUCC(3),IMPORT_FAIL(4),CLEARING(5),CLEAR_SUCC(6),CLEAR_FAIL(7)
	,EXPORTING(8),EXPORT_STOP(9),EXPORT_FAIL(10),EXPORT_FINISH(11),IMPORT_NO_NUMBER_IMPORTED(12);

	private Integer opstatus;

	OpstatusModel(Integer opstatus) {
		this.opstatus = opstatus;
	}
	
	public Integer getOpstatus() {
		return opstatus;
	}

	public void setOpstatus(Integer opstatus) {
		this.opstatus = opstatus;
	}

	public static OpstatusModel getOpstatus(Integer opstatus) {
		for (OpstatusModel om : OpstatusModel.values()) {
			if (om.getOpstatus().equals(opstatus))
				return om;
		}
		return null;
	}
	
	public String getName() {
		switch (opstatus) {
		case 0:
			return "正常";
		case 1:
			return "导入中 ";
		case 2:
			return "导入完成,部分成功";
		case 3:
			return "导入成功";
		case 4:
			return "导入失败";
		case 5:
			return "清空中";
		case 6:
			return "清空成功";
		case 7:
			return "清空失败";
		case 8:
			return "导出中";
		case 9:
			return "导出停止";
		case 10:
			return "导出失败";
		case 11:
			return "导出完成";	
		case 12:
			return "没有号码被导入";
		default:
			return "未知";
		}
		
	}
}
