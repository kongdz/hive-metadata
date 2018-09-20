package cn.zyx.mysql;

public class HiveData {
	private String table;//table 
	private String papt_name;//PART_NAME 最新分区
	private String table_id;//table_id 
	private String paprm_key;//PARAM_KEY 
	private String paprm_value;//PARAM_VALUE
	public String getTable() {
		return table;
	}
	public void setTable(String table) {
		this.table = table;
	}
	public String getPapt_name() {
		return papt_name;
	}
	public void setPapt_name(String papt_name) {
		this.papt_name = papt_name;
	}
	public String getTable_id() {
		return table_id;
	}
	public void setTable_id(String table_id) {
		this.table_id = table_id;
	}
	public String getPaprm_key() {
		return paprm_key;
	}
	public void setPaprm_key(String paprm_key) {
		this.paprm_key = paprm_key;
	}
	public String getPaprm_value() {
		return paprm_value;
	}
	public void setPaprm_value(String paprm_value) {
		this.paprm_value = paprm_value;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((paprm_key == null) ? 0 : paprm_key.hashCode());
		result = prime * result + ((paprm_value == null) ? 0 : paprm_value.hashCode());
		result = prime * result + ((papt_name == null) ? 0 : papt_name.hashCode());
		result = prime * result + ((table == null) ? 0 : table.hashCode());
		result = prime * result + ((table_id == null) ? 0 : table_id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HiveData other = (HiveData) obj;
		if (paprm_key == null) {
			if (other.paprm_key != null)
				return false;
		} else if (!paprm_key.equals(other.paprm_key))
			return false;
		if (paprm_value == null) {
			if (other.paprm_value != null)
				return false;
		} else if (!paprm_value.equals(other.paprm_value))
			return false;
		if (papt_name == null) {
			if (other.papt_name != null)
				return false;
		} else if (!papt_name.equals(other.papt_name))
			return false;
		if (table == null) {
			if (other.table != null)
				return false;
		} else if (!table.equals(other.table))
			return false;
		if (table_id == null) {
			if (other.table_id != null)
				return false;
		} else if (!table_id.equals(other.table_id))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "HiveData [table=" + table + ", papt_name=" + papt_name + ", table_id=" + table_id + ", paprm_key="
				+ paprm_key + ", paprm_value=" + paprm_value + "]";
	}
	public HiveData(String table, String papt_name, String table_id, String paprm_key, String paprm_value) {
		super();
		this.table = table;
		this.papt_name = papt_name;
		this.table_id = table_id;
		this.paprm_key = paprm_key;
		this.paprm_value = paprm_value;
	}
	public HiveData() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
