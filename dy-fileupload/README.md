文件上传服务
======
版本：*beta 1.0*  

完善中 。。。   
单元测试中，请勿在正式环境中使用   
有什么建议，有兴趣已可以一起完善   

感谢兴哥、匡工的支持


# 文档 #
----------
###项目依赖###
===
jar包及文档 [下载](http://192.168.0.133:10150/nexus/index.html#nexus-search;quick~dy-fileupload "jar以及文档")
    
###upload.properties配置:###

    ##上传图片源目录
    originalDirectory=E:/data/orgimages
    ##缩略图片生成目录
    targetDirectory=E:/data/images
    #文档文件上传根目录
    docDirectory=E:/data/docs
    #录音文件上传根目录
    recordDirectory=E:/data/records
    #flash文件上传根目录
    flashDirectory=E:/data/flashs

    #path
    #文档文件访问前缀
    docPath=/docs/
    #录音文件访问前缀
    recordPath=/records/
    #flash文件访问前缀 zip,rar文件
    flashPath=/flashs/

    #水印图片
    logoHuge=E:/data/logo_huge.png
    #图片+标题
    tileLogoPath=E:/data/logo_tile.png
    #大图水印
    superLogoPath=E:/data/logo_super.png

###代码调用###
#####*上传并且生成缩略图片*#####
    //TODO:生成缩略图目前只支持同步生成
    UploadFactory.uploadPic(picdata,"dooioo.jpg",Company.DOOIOO,new ImageArgConvert(ImageSize.IMAGE_SIZE_200x150, Logo.Logo),new ImageArgConvert(ImageSize.IMAGE_SIZE_100x75));//1
    UploadFactory.uploadPic(fileitem,Company.DOOIOO,new ImageArgConvert(ImageSize.IMAGE_SIZE_200x150, Logo.Logo),new ImageArgConvert(ImageSize.IMAGE_SIZE_100x75));//2

    List<ImageArgConvert>  imageArgConverts = new ArrayList<ImageArgConvert>(); //3
    imageArgConverts.add(new ImageArgConvert(ImageSize.IMAGE_SIZE_900x675, Logo.Logo));//4
    imageArgConverts.add(new ImageArgConvert(ImageSize.IMAGE_SIZE_100x75, Logo.Logo));//5
    UploadFactory.asyncGeneratePics("20130626/20130626163415210ERCAQ3RSXRB2Q1D.jpg",Company.DOOIOO,imageArgConverts); //6

ImageArgConvert对象：'
   
    ImageSize 图片尺寸   
    Logo:水印   
    LogoPosition：水印位置   
    async:是否异步  //TODO:异步未实现  
    mirror：是否镜像 //楼盘特殊属性   

代码块1：

    参数1：上传文件字节 eg:picdata
    参数2：上传文件名称 eg:dooioo.jpg
    参数3：公司类型，Company.DOOIOO、Company.IDERONG 所属公司,用于水印的打印
    参数4~n:ImageArgConvert对象，生成各种尺寸缩略图

代码块2：

    参数1：上传文件流 eg:fileitem
    参数2：公司类型，Company.DOOIOO、Company.IDERONG 所属公司,用于水印的打印
    参数3~n:ImageArgConvert对象，生成各种尺寸缩略图

代码块3～6：

    异步生成缩略图 //TODO：后期有单独图片服务，抽离
    参数1：上传原图路径，eg:"20130626/20130626163415210ERCAQ3RSXRB2Q1D.jpg"
    参数2：公司类型，Company.DOOIOO、Company.IDERONG 所属公司,用于水印的打印
    参数3：List<ImageArgConvert>对象，生成各种尺寸缩略图


**上传、录音、zip、rar等文档文件，无需生成缩略图**

     System.out.println(UploadFactory.upload(docdata,"QQ空间.apk",false)); //1
     System.out.println(UploadFactory.upload(recorddata,"dooioo.wav",false));//2

     System.out.println("不解压:" + UploadFactory.upload(rardata,"TelServer.rar",false)); //3
     System.out.println("解压:" + UploadFactory.upload(rardata,"TelServer.rar",true));//4

代码块1、2：
    上传文档、录音等文件，无需解压

代码块3、4：
    上传rar、zip文件，3是无需解压、4是文件解压


上传后对象返回UploadResult
===
    origiName:上传源文件名称
    targetName:生成后文件路径+名称

    //图片特有信息
    width:上传图片的宽
    height:上传图片的高

    //zip ,房源全景，默认处理
    htmPath:默认选择一个html当作访问地址
    iconPath: 默认选择一张图片当作默认预览图，无压缩

