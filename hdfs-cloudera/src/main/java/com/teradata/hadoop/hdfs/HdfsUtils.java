package com.teradata.hadoop.hdfs;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.PrivilegedExceptionAction;
import java.util.ArrayList;
import java.util.List;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.PathFilter;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.security.UserGroupInformation;

public class HdfsUtils {
	private static final String OOZIE_USER_OOZIEWEB = "oozie.user.oozieweb";
	private static final String KEYTAB_FILE_OOZIEWEB = "keytab.file.oozieweb";

	private static String oozieUserOozieweb = "oozieweb";
	private static String keyfile_oozieWeb;
	private boolean result = false;

	static {
		CfgUtils cfg = new CfgUtils();
		if (cfg.getProperty(OOZIE_USER_OOZIEWEB) != null){
			oozieUserOozieweb = cfg.getProperty(OOZIE_USER_OOZIEWEB);
		}

		keyfile_oozieWeb = cfg.getProperty(KEYTAB_FILE_OOZIEWEB);

	}
	private Configuration conf = null;

	private boolean copyHdfsFileToHdfsDir_flg;
	private boolean delFile_flg;
	private boolean downloadFile_flg;
	private List<String> dirList;

	public HdfsUtils() {
		conf = new HadoopLoginUtils().loginHdfs(oozieUserOozieweb, keyfile_oozieWeb);
	}

	/*
	 * public HdfsUtils(String oozieUser, String keyfile) { conf = new
	 * HadoopLoginUtils().loginHdfs(oozieUser, keyfile); }
	 */

	public boolean exists(Path hdfsPath) throws IOException {
		FileSystem fs = FileSystem.get(conf);
		return fs.equals(hdfsPath);
	}

	public boolean copyHdfsDirFilesToHdfsDir(String source, String target) {
		try {
			FileSystem fs = FileSystem.get(conf);
			Path sourcePath = new Path(source);

			FileStatus[] sfiles = fs.listStatus(sourcePath);
			for (FileStatus sfi : sfiles) {
				if (sfi.getPath().getName().endsWith(".jar")){
					copyHdfsFileToHdfsDir(sfi.getPath(), target);
				}

				System.out.println(sfi.getPath().toString());
			}
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean copyHdfsFileToHdfsDir(Path filePath, String targetPath) {
		FSDataOutputStream fsOut = null;
		FSDataInputStream fsIn = null;
		try {
			FileSystem hadoopFS = FileSystem.get(conf);

			fsOut = hadoopFS.create(new Path(targetPath + "/" + filePath.getName()));
			fsIn = hadoopFS.open(filePath);
			byte[] buf = new byte[1024];
			int readbytes = 0;
			while ((readbytes = fsIn.read(buf)) > 0) {
				fsOut.write(buf, 0, readbytes);
			}

			return true;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fsIn != null){
				try {
					fsIn.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			if (fsOut != null){
				try {
					fsOut.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}

	public boolean copyHdfsDirFilesToHdfsDir(String oozieUser, final String source, final String target) throws IOException {
		if (oozieUserOozieweb.equals(oozieUser)) {
			return copyHdfsDirFilesToHdfsDir(source, target);
		} else {

			UserGroupInformation ugi = UserGroupInformation.createProxyUser(oozieUser, UserGroupInformation.getLoginUser());
			try {
				ugi.doAs(new PrivilegedExceptionAction<Void>() {
					public Void run() {
						copyHdfsFileToHdfsDir_flg = copyHdfsDirFilesToHdfsDir(source, target);
						return null;
					}
				});
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			return copyHdfsFileToHdfsDir_flg;
		}
	}

	public boolean sendFile(String path, String localfile) {
		System.out.println("path:" + path + "......localfile:" + localfile);
		File file = new File(localfile);
		if (!file.isFile()) {
			System.out.println(file.getName());
			return false;
		}
		FSDataOutputStream fsOut = null;
		FSDataInputStream fsIn = null;
		try {
			FileSystem localFS = FileSystem.getLocal(conf);
			FileSystem hadoopFS = FileSystem.get(conf);

			fsOut = hadoopFS.create(new Path(path + "/" + file.getName()));
			fsIn = localFS.open(new Path(localfile));
			byte[] buf = new byte[1024];
			int readbytes = 0;
			while ((readbytes = fsIn.read(buf)) > 0) {
				fsOut.write(buf, 0, readbytes);
			}
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fsIn != null){
				try {
					fsIn.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (fsOut != null){
				try {
					fsOut.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}

	public boolean sendFile(String oozieUser, final String path, final String localfile) throws IOException {
		result = false;
		if (oozieUserOozieweb.equals(oozieUser)) {
			result = sendFile(path, localfile);
		} else {
			UserGroupInformation ugi = UserGroupInformation.createProxyUser(oozieUser, UserGroupInformation.getLoginUser());
			try {
				ugi.doAs(new PrivilegedExceptionAction<Void>() {
					public Void run() {
						result = sendFile(path, localfile);
						return null;
					}
				});
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public void mkdirs(final String folder) throws IOException {
		FileSystem fs = null;
		try {
			fs = FileSystem.get(conf);
			Path path = new Path(folder);
			fs.mkdirs(path);
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (fs != null){
					fs.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void mkdirs(String oozieUser, final String folder) throws IOException {
		if (oozieUserOozieweb.equals(oozieUser)) {
			mkdirs(folder);
		} else {
			UserGroupInformation ugi = UserGroupInformation.createProxyUser(oozieUser, UserGroupInformation.getLoginUser());
			try {
				ugi.doAs(new PrivilegedExceptionAction<Void>() {
					@Override
					public Void run() {
						try {
							mkdirs(folder);
						} catch (IOException e) {
							e.printStackTrace();
						}
						return null;
					}
				});
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public boolean delFile(String hadfile) {
		try {
			FileSystem hadoopFS = FileSystem.get(conf);
			Path hadPath = new Path(hadfile);
			Path p = hadPath.getParent();
			boolean rtnval = hadoopFS.delete(hadPath, true);
			System.out.println("delFile file: " + hadPath);
			FileStatus[] hadfiles = hadoopFS.listStatus(p);
			for (FileStatus fs : hadfiles) {
				System.out.println(fs.toString());
			}
			return rtnval;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean delFile(String oozieUser, final String hadfile) {
		if (oozieUserOozieweb.equals(oozieUser)) {
			return delFile(hadfile);
		} else {
			try {
				UserGroupInformation ugi = UserGroupInformation.createProxyUser(oozieUser, UserGroupInformation.getLoginUser());

				ugi.doAs(new PrivilegedExceptionAction<Void>() {
					public Void run() {
						delFile_flg = delFile(hadfile);
						return null;
					}
				});
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return delFile_flg;
		}
	}

	public boolean downloadFile(String hadfile, String localPath) {
		FSDataInputStream fsIn = null;
		FileWriter fos = null;
		try {

			FileSystem hadoopFS = FileSystem.get(conf);
			Path hadPath = new Path(hadfile);
			fsIn = hadoopFS.open(hadPath);

			BufferedReader bis = new BufferedReader(new InputStreamReader(fsIn, "utf-8"));

			File localFile = new File(localPath, hadPath.getName());
			if (!localFile.exists()) {
				localFile.createNewFile();
			}
			fos = new FileWriter(localFile);

			BufferedWriter bw = new BufferedWriter(fos);

			String temp;

			while ((temp = bis.readLine()) != null) {
				bw.write(temp + "\n");
			}
			bw.close();
			bis.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fsIn != null){
				try {
					fsIn.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			if (fos != null){
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}

	public boolean downloadFile(String oozieUser, final String hadfile, final String localPath) {
		if (oozieUserOozieweb.equals(oozieUser)) {
			return downloadFile(hadfile, localPath);
		} else {
			try {
				UserGroupInformation ugi = UserGroupInformation.createProxyUser(oozieUser, UserGroupInformation.getLoginUser());

				ugi.doAs(new PrivilegedExceptionAction<Void>() {
					@Override
					public Void run() {
						downloadFile_flg = downloadFile(hadfile, localPath);
						return null;
					}
				});
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return downloadFile_flg;
		}
	}

	public void createEmptyFile(String dir, String filename) throws IOException {
		FileSystem fs = FileSystem.get(conf);
		try {
			Path parentPath = new Path(dir);
			Path thisPath = new Path(parentPath, filename);
			if (!fs.exists(thisPath)) {
				fs.create(thisPath);
				System.out.println("Create file: " + thisPath);
			}
		} finally {
			fs.close();
		}
	}

	public void createEmptyFile(String oozieUser, final String dir, final String filename) throws IOException {
		if (oozieUserOozieweb.equals(oozieUser)) {
			createEmptyFile(dir, filename);
		} else {
			UserGroupInformation ugi = UserGroupInformation.createProxyUser(oozieUser, UserGroupInformation.getLoginUser());
			try {
				ugi.doAs(new PrivilegedExceptionAction<Void>() {
					public Void run() {
						try {
							createEmptyFile(dir, filename);
						} catch (IOException e) {
							e.printStackTrace();
						}
						return null;
					}
				});
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void createFile(String file, String content) throws IOException {
		FileSystem fs = FileSystem.get(conf);
		byte[] buff = content.getBytes();
		FSDataOutputStream os = null;
		try {
			os = fs.create(new Path(file));
			os.write(buff, 0, buff.length);
			System.out.println("Create: " + file);
		} finally {
			if (os != null){
				os.close();
			}
		}
		fs.close();
	}

	public void createFile(String oozieUser, final String file, final String content) throws IOException {
		if (oozieUserOozieweb.equals(oozieUser)) {
			createFile(file, content);
		} else {
			UserGroupInformation ugi = UserGroupInformation.createProxyUser(oozieUser, UserGroupInformation.getLoginUser());
			try {
				ugi.doAs(new PrivilegedExceptionAction<Void>() {
					@Override
					public Void run() {
						try {
							createFile(file, content);
						} catch (IOException e) {
							e.printStackTrace();
						}
						return null;
					}
				});
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public List<String> listDir(String dir, final String findWord) throws IOException {
		FileSystem fs = FileSystem.get(conf);
		FileStatus[] status = fs.globStatus(new Path(dir + "*"), new PathFilter() {
			@Override
			public boolean accept(Path path) {
				boolean result = path.toString().substring(path.toString().lastIndexOf("/")).contains(findWord);
				return result;
			}

		});
		List<String> list = new ArrayList<String>();
		String outdir = "";
		if (!dir.endsWith("/")) {
			outdir = dir + "/";
		} else {
			outdir = dir;
		}
		for (FileStatus file : status) {
			if (file.isDirectory()) {
				list.add(outdir + file.getPath().getName());
			}
		}
		return list;
	}

	public List<String> listDir(String oozieUser, final String dir, final String findWord) throws IOException, InterruptedException {
		if (oozieUserOozieweb.equals(oozieUser)) {
			dirList = listDir(dir, findWord);
		} else {
			UserGroupInformation ugi = UserGroupInformation.createProxyUser(oozieUser, UserGroupInformation.getLoginUser());
			ugi.doAs(new PrivilegedExceptionAction<Void>() {
				public Void run() {
					try {
						dirList = listDir(dir, findWord);
					} catch (IOException e) {
						e.printStackTrace();
					}
					return null;
				}
			});
		}
		return dirList;
	}

	public void readFile(String filePath) throws IOException {
		Path srcPath = new Path(filePath);
		FileSystem fs = null;
		URI uri;
		try {
			uri = new URI(filePath);
			fs = FileSystem.get(uri, conf);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		InputStream in = null;
		try {
			in = fs.open(srcPath);
			IOUtils.copyBytes(in, System.out, 4096, false);
		} finally {
			IOUtils.closeStream(in);
		}
	}

	public static void main(String[] args) throws IOException {
		HdfsUtils hdfs=new HdfsUtils();
//		hdfs.listDir("/", "");
//		hdfs.listDir("/user");
		hdfs.readFile("/user/oozieweb/data/encrd.txt");
	}
}
