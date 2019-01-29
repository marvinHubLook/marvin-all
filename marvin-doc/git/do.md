* 查看版本,格式化输出
> git log --graph --pretty=oneline --abbrev-commit

* 对比差异
> git diff    # 是工作区(work dict)和暂存区(stage)的比较  
 git diff --cached    # 是暂存区(stage)和分支(master)的比较

* 合并
> git merge --no-ff -m "merge with no-ff" dev

* 拉取远程分支
> git checkout -b dev origin/dev

* 删除某个远程版本tag
> git push origin :refs/tags/v0.9  #删除远程某个tag
 
* 查看哪些分支还没有合并
> git branch --no-merged

* 拉取文件，推送文件
> git fetch   #命令从服务器上抓取本地没有的数据时，它并不会修改工作目录中的内容。 它只会获取数据然后让你自己合并  
git pull    #在大多数情况下它的含义是一个 git fetch 紧接着一个 git merge 命令


