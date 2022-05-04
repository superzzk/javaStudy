namespace java gen

service TestThriftService
{
	/**
	*value 中存放两个字符串拼接之后的字符串
	*/
	string getStr(1:string srcStr1, 2:string srcStr2),
	i32 getInt(1:i32 val)
}