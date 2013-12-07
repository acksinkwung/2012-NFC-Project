using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using CommunicationNFC;
namespace NFCDLLSample
{
    public partial class MainForm : Form
    {
        public MainForm()
        {
            InitializeComponent();
        }

        private void btnActive(object sender, EventArgs e)
        {
            MessageBox.Show(NFC.communicationActive("active"),"Message");
        }

        private void btnPassive(object sender, EventArgs e)
        {
            MessageBox.Show(NFC.communicationPassive("passive"), "Message");
        }
    }
}
