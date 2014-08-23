" Disable syntastic checks on file write
let g:syntastic_mode_map = {"mode": "passive" }

" Map build function
map \r :!ant debug && adb install -r bin/HelloWorld-debug.apk<CR>
map \l :!adb logcat
