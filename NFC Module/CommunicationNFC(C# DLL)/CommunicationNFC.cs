using System;
using System.Diagnostics;
namespace CommunicationNFC
{
     public class NFC {
         /// <summary>
         /// 主動接收NFC訊息
         /// </summary>
         /// <param name="message">欲傳送的訊息</param>
         /// <returns>接收的訊息</returns>
		 public static String communicationActive(string message) {
			String msgCommand;
			while (true)
            {
                msgCommand = NFC.communicationPassive(message);
                if (msgCommand != "")
                {
					return msgCommand;
                } 
            }
		 }         
         /// <summary>
         /// 被動接收NFC訊息
         /// </summary>
         /// <param name="message">欲傳送的訊息</param>
         /// <returns>接收的訊息</returns>
		 public static String communicationPassive(string message)
		 {
			ProcessStartInfo psi;
			Process proc;
			psi = new ProcessStartInfo("NFCSRModule.exe");
			psi.Arguments = message;
			psi.RedirectStandardInput = true;
			psi.RedirectStandardOutput = true;
			psi.RedirectStandardError = true;
			psi.UseShellExecute = false;
			psi.CreateNoWindow = true;
			proc = Process.Start(psi);
			String msgCommand = proc.StandardOutput.ReadToEnd();
			proc.WaitForExit();
			
			if (msgCommand.IndexOf("Message:") != -1)
			{
				msgCommand = msgCommand.Substring(msgCommand.IndexOf("Message:") + 8);
			}
			else
			{
				msgCommand = "";
			}
			proc.Close();
			return msgCommand;
		 }
	 }
}