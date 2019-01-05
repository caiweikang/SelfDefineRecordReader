package com.vip.selfdefinerecordreader;

import java.io.IOException;
import java.nio.file.FileSystem;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.LineRecordReader.LineReader;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
/**
 *        �Զ���RecordReader
 * @author weikang.cai
 * @since 1.8
 *
 */
public class SelfDefineRecordReader extends RecordReader<IntWritable, Text>{
	private FileSplit fileSplit;
	private IntWritable key;
	private Text value;
	private org.apache.hadoop.util.LineReader reader;
	private int count;
	
	@Override
	public void initialize(InputSplit split, TaskAttemptContext context) throws IOException, InterruptedException {
		// 1.get fileSplit
		fileSplit = (FileSplit) split;
		// 2.get path
		Path path = fileSplit.getPath();
		Configuration configuration = new Configuration();
		// 3.get fileSystem
		org.apache.hadoop.fs.FileSystem fileSystem = path.getFileSystem(configuration);
		// 4.get inputStream
		FSDataInputStream inputStream = fileSystem.open(path);
		// 5.get lineReader
		reader = new org.apache.hadoop.util.LineReader(inputStream);
	}
	/**
	 * 1.���˷����ķ���ֵΪtrue�Ǿͻᱻ����һ��
	 * 2.getCurrentKey()��getCurrentValue()Ҳ�ᱻ�����ĵ���һ��
	 */
	@Override
	public boolean nextKeyValue() throws IOException, InterruptedException {
		key = new IntWritable();
		value = new Text();
		Text tmp = new Text(); // ��ʱ���readLine��һ������
		int result = reader.readLine(tmp);
		if(result == 0) {
			return false;
		} else {
			count++;
			key.set(count);
			value = tmp;
			return true;
		}
	}
	
	/**
	 *  �����keyΪmap()������key
	 */
	@Override
	public IntWritable getCurrentKey() throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		return key;
	}
	
	/**
	 * �����valueΪmap()������value
	 */
	@Override
	public Text getCurrentValue() throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		return value;
	}
	
	@Override
	public void close() throws IOException {
		// TODO Auto-generated method stub
		if(null != reader) {
			reader = null;
		}
	}
	
	@Override
	public float getProgress() throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
}
