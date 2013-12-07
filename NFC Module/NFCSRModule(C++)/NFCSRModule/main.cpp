#include "nfc/nfc.h"
#include "nfc/nfc-messages.h"
#include "stdlib.h"
#include <stdio.h> 
#include <string.h>
#include <iostream>
#include <Windows.h>
#include <process.h>
#include "windows.h" 

#define MAX_FRAME_LEN 264
static nfc_device_t *pnd;
byte_t abtRx[MAX_FRAME_LEN];
size_t szRx;


bool connectNFC();
void send(char* str);
char* received();

void main(char argc,char* argv[]) {
	pnd = nfc_connect (NULL);
	if (pnd==NULL)
		return;
	// 連結NFC Reader
	if (connectNFC()==false)
		return;	
	if (argc>1) 
		send(argv[1]);
	else
		send("");
	char *rResult = received();
	printf("Message:%s",rResult);
	
	nfc_disconnect(pnd);
}
bool connectNFC() {
	// 取得NFC Tag
	nfc_target_t nt;
	nt.nm.nmt = NMT_ISO14443A;
	nt.nm.nbr = NBR_UNDEFINED; // Will be updated by nfc_target_init()
	nt.nti.nai.abtAtqa[0] = 0x00;
	nt.nti.nai.abtAtqa[1] = 0x04;
	nt.nti.nai.abtUid[0] = 0x08;
	nt.nti.nai.abtUid[1] = 0x00;
	nt.nti.nai.abtUid[2] = 0xb0;
	nt.nti.nai.abtUid[3] = 0x0b;
	nt.nti.nai.btSak = 0x20;
	nt.nti.nai.szUidLen = 4;
	nt.nti.nai.szAtsLen = 0;
	// 初始建立連接NFC手機
	bool result;
	result = nfc_target_init(pnd, &nt, abtRx, &szRx);	
	if (result==false) {
		return false;
	}
	result = nfc_target_receive_bytes(pnd,abtRx,&szRx);	
	result = nfc_target_send_bytes(pnd,(const byte_t*)"\x6a\x87",2);		
	result = nfc_target_receive_bytes(pnd,abtRx,&szRx);		
	result = nfc_target_send_bytes(pnd,(const byte_t*)"\x6a\x87",2);	
	result = nfc_target_receive_bytes(pnd,abtRx,&szRx);	
	if (result==false) {
		return false;
	}
	return true;
}
//傳送
void send(char* str) {
	int len = strlen(str);
	int lenSend = 0;

	do {
		if (len > 254) {
			lenSend = 254;
			bool result = nfc_target_send_bytes(pnd,(const byte_t*)str,lenSend);
			result = nfc_target_receive_bytes(pnd,abtRx,&szRx);	

		} else {
			lenSend = len;
			bool result = nfc_target_send_bytes(pnd,(const byte_t*)str,lenSend);
			result = nfc_target_receive_bytes(pnd,abtRx,&szRx);	
			result = nfc_target_send_bytes(pnd,(const byte_t*)"\x01",1);


			return ;			
		}
		str = str + 254;
		len = len - 254;
	} while(true);
	return ;
	
}
//接收
char* received() {
	char *content;
	content = (char*)malloc(sizeof(char)*1024);
	int n = 0;
	
	bool result;
	do {
		result = nfc_target_receive_bytes(pnd,abtRx,&szRx);
		if (abtRx[0]==0x01) {
			result = nfc_target_send_bytes(pnd,(const byte_t*)"\x01",1);
			break;
		}
		for (int i=1; i<11; i++) {
			try {
				content[n] = abtRx[i];
			}catch(int err){
				content[n] = 0x00;
			}
			n++;
		};
		result = nfc_target_send_bytes(pnd,(const byte_t*)"\x01",1);

	} while (true);	
	return content;
}