#!/bin/sh
fallocate -l 16M file.bin

u1() {
echo "Uploading file 1 part 1/4"
curl -T file.bin --progress-bar -H "Content-Range: bytes 0-16777215/67108864" http://localhost:8080/upload/file-test-1.bin | tee /dev/null
echo "Uploading file 1 part 2/4"
curl -T file.bin --progress-bar -H "Content-Range: bytes 16777216-33554431/67108864" http://localhost:8080/upload/file-test-1.bin | tee /dev/null
echo "Uploading file 1 part 3/4"
curl -T file.bin --progress-bar -H "Content-Range: bytes 33554432-50331647/67108864" http://localhost:8080/upload/file-test-1.bin | tee /dev/null
echo "Uploading file 1 part 4/4"
curl -T file.bin --progress-bar -H "Content-Range: bytes 50331648-67108863/67108864" http://localhost:8080/upload/file-test-1.bin | tee /dev/null
echo "Uploaded file 1"
}
u2() {
echo "Uploading file 2 part 1/4"
curl -T file.bin --progress-bar -H "Content-Range: bytes 0-16777215/67108864" http://localhost:8080/upload/file-test-2.bin | tee /dev/null
echo "Uploading file 2 part 2/4"
curl -T file.bin --progress-bar -H "Content-Range: bytes 16777216-33554431/67108864" http://localhost:8080/upload/file-test-2.bin | tee /dev/null
echo "Uploading file 2 part 3/4"
curl -T file.bin --progress-bar -H "Content-Range: bytes 33554432-50331647/67108864" http://localhost:8080/upload/file-test-2.bin | tee /dev/null
echo "Uploading file 2 part 4/4"
curl -T file.bin --progress-bar -H "Content-Range: bytes 50331648-67108863/67108864" http://localhost:8080/upload/file-test-2.bin | tee /dev/null
echo "Uploaded file 2"
}
u3() {
echo "Uploading file 3 part 1/4"
curl -T file.bin --progress-bar -H "Content-Range: bytes 0-16777215/67108864" http://localhost:8080/upload/file-test-3.bin | tee /dev/null
echo "Uploading file 3 part 2/4"
curl -T file.bin --progress-bar -H "Content-Range: bytes 16777216-33554431/67108864" http://localhost:8080/upload/file-test-3.bin | tee /dev/null
echo "Uploading file 3 part 3/4"
curl -T file.bin --progress-bar -H "Content-Range: bytes 33554432-50331647/67108864" http://localhost:8080/upload/file-test-3.bin | tee /dev/null
echo "Uploading file 3 part 4/4"
curl -T file.bin --progress-bar -H "Content-Range: bytes 50331648-67108863/67108864" http://localhost:8080/upload/file-test-3.bin | tee /dev/null
echo "Uploaded file 3"
}
u4() {
echo "Uploading file 4 part 1/4"
curl -T file.bin --progress-bar -H "Content-Range: bytes 0-16777215/67108864" http://localhost:8080/upload/file-test-4.bin | tee /dev/null
echo "Uploading file 4 part 2/4"
curl -T file.bin --progress-bar -H "Content-Range: bytes 16777216-33554431/67108864" http://localhost:8080/upload/file-test-4.bin | tee /dev/null
echo "Uploading file 4 part 3/4"
curl -T file.bin --progress-bar -H "Content-Range: bytes 33554432-50331647/67108864" http://localhost:8080/upload/file-test-4.bin | tee /dev/null
echo "Uploading file 4 part 4/4"
curl -T file.bin --progress-bar -H "Content-Range: bytes 50331648-67108863/67108864" http://localhost:8080/upload/file-test-4.bin | tee /dev/null
echo "Uploaded file 4"
}
u5() {
echo "Uploading file 5 part 1/4"
curl -T file.bin --progress-bar -H "Content-Range: bytes 0-16777215/67108864" http://localhost:8080/upload/file-test-5.bin | tee /dev/null
echo "Uploading file 5 part 2/4"
curl -T file.bin --progress-bar -H "Content-Range: bytes 16777216-33554431/67108864" http://localhost:8080/upload/file-test-5.bin | tee /dev/null
echo "Uploading file 5 part 3/4"
curl -T file.bin --progress-bar -H "Content-Range: bytes 33554432-50331647/67108864" http://localhost:8080/upload/file-test-5.bin | tee /dev/null
echo "Uploading file 5 part 4/4"
curl -T file.bin --progress-bar -H "Content-Range: bytes 50331648-67108863/67108864" http://localhost:8080/upload/file-test-5.bin | tee /dev/null
echo "Uploaded file 5"
}
u6() {
echo "Uploading file 6 part 1/4"
curl -T file.bin --progress-bar -H "Content-Range: bytes 0-16777215/67108864" http://localhost:8080/upload/file-test-6.bin | tee /dev/null
echo "Uploading file 6 part 2/4"
curl -T file.bin --progress-bar -H "Content-Range: bytes 16777216-33554431/67108864" http://localhost:8080/upload/file-test-6.bin | tee /dev/null
echo "Uploading file 6 part 3/4"
curl -T file.bin --progress-bar -H "Content-Range: bytes 33554432-50331647/67108864" http://localhost:8080/upload/file-test-6.bin | tee /dev/null
echo "Uploading file 6 part 4/4"
curl -T file.bin --progress-bar -H "Content-Range: bytes 50331648-67108863/67108864" http://localhost:8080/upload/file-test-6.bin | tee /dev/null
echo "Uploaded file 6"
}
u7() {
echo "Uploading file 7 part 1/4"
curl -T file.bin --progress-bar -H "Content-Range: bytes 0-16777215/67108864" http://localhost:8080/upload/file-test-7.bin | tee /dev/null
echo "Uploading file 7 part 2/4"
curl -T file.bin --progress-bar -H "Content-Range: bytes 16777216-33554431/67108864" http://localhost:8080/upload/file-test-7.bin | tee /dev/null
echo "Uploading file 7 part 3/4"
curl -T file.bin --progress-bar -H "Content-Range: bytes 33554432-50331647/67108864" http://localhost:8080/upload/file-test-7.bin | tee /dev/null
echo "Uploading file 7 part 4/4"
curl -T file.bin --progress-bar -H "Content-Range: bytes 50331648-67108863/67108864" http://localhost:8080/upload/file-test-7.bin | tee /dev/null
echo "Uploaded file 7"
}
u8() {
echo "Uploading file 8 part 1/4"
curl -T file.bin --progress-bar -H "Content-Range: bytes 0-16777215/67108864" http://localhost:8080/upload/file-test-8.bin | tee /dev/null
echo "Uploading file 8 part 2/4"
curl -T file.bin --progress-bar -H "Content-Range: bytes 16777216-33554431/67108864" http://localhost:8080/upload/file-test-8.bin | tee /dev/null
echo "Uploading file 8 part 3/4"
curl -T file.bin --progress-bar -H "Content-Range: bytes 33554432-50331647/67108864" http://localhost:8080/upload/file-test-8.bin | tee /dev/null
echo "Uploading file 8 part 4/4"
curl -T file.bin --progress-bar -H "Content-Range: bytes 50331648-67108863/67108864" http://localhost:8080/upload/file-test-8.bin | tee /dev/null
echo "Uploaded file 8"
}
u9() {
echo "Uploading file 9 part 1/4"
curl -T file.bin --progress-bar -H "Content-Range: bytes 0-16777215/67108864" http://localhost:8080/upload/file-test-9.bin | tee /dev/null
echo "Uploading file 9 part 2/4"
curl -T file.bin --progress-bar -H "Content-Range: bytes 16777216-33554431/67108864" http://localhost:8080/upload/file-test-9.bin | tee /dev/null
echo "Uploading file 9 part 3/4"
curl -T file.bin --progress-bar -H "Content-Range: bytes 33554432-50331647/67108864" http://localhost:8080/upload/file-test-9.bin | tee /dev/null
echo "Uploading file 9 part 4/4"
curl -T file.bin --progress-bar -H "Content-Range: bytes 50331648-67108863/67108864" http://localhost:8080/upload/file-test-9.bin | tee /dev/null
echo "Uploaded file 9"
}
u10() {
echo "Uploading file 10 part 1/4"
curl -T file.bin --progress-bar -H "Content-Range: bytes 0-16777215/67108864" http://localhost:8080/upload/file-test-10.bin | tee /dev/null
echo "Uploading file 10 part 2/4"
curl -T file.bin --progress-bar -H "Content-Range: bytes 16777216-33554431/67108864" http://localhost:8080/upload/file-test-10.bin | tee /dev/null
echo "Uploading file 10 part 3/4"
curl -T file.bin --progress-bar -H "Content-Range: bytes 33554432-50331647/67108864" http://localhost:8080/upload/file-test-10.bin | tee /dev/null
echo "Uploading file 10 part 4/4"
curl -T file.bin --progress-bar -H "Content-Range: bytes 50331648-67108863/67108864" http://localhost:8080/upload/file-test-10.bin | tee /dev/null
echo "Uploaded file 10"
}
u1 & u2 & u3 & u4 & u5 & u6 & u7 & u8 & u9 & u10
wait