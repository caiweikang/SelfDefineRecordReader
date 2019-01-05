package com.vip.selfdefinerecordreader;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.MultipleInputs;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class WordCountDriver {

	public static void main(String[] args) throws Exception{
		Configuration configuration = new Configuration();
		Job job = Job.getInstance(configuration);
		
		// 1.����Job������������
		job.setJarByClass(WordCountDriver.class);
		// ����Mapper������е���
		job.setMapperClass(WordCountMapper.class);
		
		// 2.����Mapper������key������
		job.setMapOutputKeyClass(IntWritable.class);
		// ����Mapper������value����
		job.setMapOutputValueClass(Text.class);
		
		// *ָ���Զ����inputFormat
		job.setInputFormatClass(SelfDefineInputFormat.class);
		//MultipleInputs.addInputPath(job, new Path("hdfs://192.168.154.129:9000/selfdefinerecordreader/words.txt"), SelfDefineInputFormat.class, WordCountMapper.class);
		
		// 3.����job�������ļ����ڵ�HDFS·��
		FileInputFormat.setInputPaths(job, new Path("hdfs://192.168.154.129:9000/selfdefinerecordreader/words.txt"));
		// ���ý���ļ�·����Ҳ�����Ǵ洢HDFS��
		FileOutputFormat.setOutputPath(job, new Path("hdfs://192.168.154.129:9000/selfdefinerecordreader/result"));
		
		// 4.�ύ����
		job.waitForCompletion(true);
	}

}
