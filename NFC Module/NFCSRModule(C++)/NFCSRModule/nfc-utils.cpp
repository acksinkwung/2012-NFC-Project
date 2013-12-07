/*-
 * Public platform independent Near Field Communication (NFC) library examples
 * 
 * Copyright (C) 2009, Roel Verdult
 * Copyright (C) 2010, Romuald Conty, Romain Tartière
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *  1) Redistributions of source code must retain the above copyright notice,
 *  this list of conditions and the following disclaimer. 
 *  2 )Redistributions in binary form must reproduce the above copyright
 *  notice, this list of conditions and the following disclaimer in the
 *  documentation and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 * 
 * Note that this license only applies on the examples, NFC library itself is under LGPL
 *
 */

#include <nfc/nfc.h>
#include <nfc/nfc-types.h>
#include "nfc-utils.h"
#include "Stdafx.h"

static const byte OddParity[256] = {
  1, 0, 0, 1, 0, 1, 1, 0, 0, 1, 1, 0, 1, 0, 0, 1,
  0, 1, 1, 0, 1, 0, 0, 1, 1, 0, 0, 1, 0, 1, 1, 0,
  0, 1, 1, 0, 1, 0, 0, 1, 1, 0, 0, 1, 0, 1, 1, 0,
  1, 0, 0, 1, 0, 1, 1, 0, 0, 1, 1, 0, 1, 0, 0, 1,
  0, 1, 1, 0, 1, 0, 0, 1, 1, 0, 0, 1, 0, 1, 1, 0,
  1, 0, 0, 1, 0, 1, 1, 0, 0, 1, 1, 0, 1, 0, 0, 1,
  1, 0, 0, 1, 0, 1, 1, 0, 0, 1, 1, 0, 1, 0, 0, 1,
  0, 1, 1, 0, 1, 0, 0, 1, 1, 0, 0, 1, 0, 1, 1, 0,
  0, 1, 1, 0, 1, 0, 0, 1, 1, 0, 0, 1, 0, 1, 1, 0,
  1, 0, 0, 1, 0, 1, 1, 0, 0, 1, 1, 0, 1, 0, 0, 1,
  1, 0, 0, 1, 0, 1, 1, 0, 0, 1, 1, 0, 1, 0, 0, 1,
  0, 1, 1, 0, 1, 0, 0, 1, 1, 0, 0, 1, 0, 1, 1, 0,
  1, 0, 0, 1, 0, 1, 1, 0, 0, 1, 1, 0, 1, 0, 0, 1,
  0, 1, 1, 0, 1, 0, 0, 1, 1, 0, 0, 1, 0, 1, 1, 0,
  0, 1, 1, 0, 1, 0, 0, 1, 1, 0, 0, 1, 0, 1, 1, 0,
  1, 0, 0, 1, 0, 1, 1, 0, 0, 1, 1, 0, 1, 0, 0, 1
};

byte
oddparity (const byte bt)
{
  return OddParity[bt];
}

void
oddparity_bytes_ts (const byte * pbtData, const size_t szLen, byte * pbtPar)
{
  size_t  szByteNr;
  // Calculate the parity bits for the command
  for (szByteNr = 0; szByteNr < szLen; szByteNr++) {
    pbtPar[szByteNr] = OddParity[pbtData[szByteNr]];
  }
}

void
print_hex (const byte * pbtData, const size_t szBytes)
{
  size_t  szPos;

  for (szPos = 0; szPos < szBytes; szPos++) {
    printf ("%02x  ", pbtData[szPos]);
  }
  printf ("\n");
}

void
print_hex_bits (const byte * pbtData, const size_t szBits)
{
  unsigned char uRemainder;
  size_t  szPos;
  size_t  szBytes = szBits / 8;

  for (szPos = 0; szPos < szBytes; szPos++) {
    printf ("%02x  ", pbtData[szPos]);
  }

  uRemainder = szBits % 8;
  // Print the rest bits
  if (uRemainder != 0) {
    if (uRemainder < 5)
      printf ("%01x (%d bits)", pbtData[szBytes], uRemainder);
    else
      printf ("%02x (%d bits)", pbtData[szBytes], uRemainder);
  }
  printf ("\n");
}

void
print_hex_par (const byte * pbtData, const size_t szBits, const byte * pbtDataPar)
{
  unsigned char uRemainder;
  size_t  szPos;
  size_t  szBytes = szBits / 8;

  for (szPos = 0; szPos < szBytes; szPos++) {
    printf ("%02x", pbtData[szPos]);
    if (OddParity[pbtData[szPos]] != pbtDataPar[szPos]) {
      printf ("! ");
    } else {
      printf ("  ");
    }
  }

  uRemainder = szBits % 8;
  // Print the rest bits, these cannot have parity bit
  if (uRemainder != 0) {
    if (uRemainder < 5)
      printf ("%01x (%d bits)", pbtData[szBytes], uRemainder);
    else
      printf ("%02x (%d bits)", pbtData[szBytes], uRemainder);
  }
  printf ("\n");
}

#define SAK_UID_NOT_COMPLETE     0x04
#define SAK_ISO14443_4_COMPLIANT 0x20
#define SAK_ISO18092_COMPLIANT   0x40


#define PI_ISO14443_4_SUPPORTED 0x01
#define PI_NAD_SUPPORTED        0x01
#define PI_CID_SUPPORTED        0x02
void print_nfc_iso14443b_info (const nfc_iso14443b_info_t nbi, bool verbose)
{
  const int iMaxFrameSizes[] = { 16, 24, 32, 40, 48, 64, 96, 128, 256 };
  printf ("               PUPI: ");
  print_hex (nbi.abtPupi, 4);
  printf ("   Application Data: ");
  print_hex (nbi.abtApplicationData, 4);
  printf ("      Protocol Info: ");
  print_hex (nbi.abtProtocolInfo, 3);
  if (verbose) {
    printf ("* Bit Rate Capability:\n");
    if (nbi.abtProtocolInfo[0] == 0) {
      printf (" * PICC supports only 106 kbits/s in both directions\n");
    }
    if (nbi.abtProtocolInfo[0] & 1<<7) {
      printf (" * Same bitrate in both directions mandatory\n");
    }
    if (nbi.abtProtocolInfo[0] & 1<<4) {
      printf (" * PICC to PCD, 1etu=64/fc, bitrate 212 kbits/s supported\n");
    }
    if (nbi.abtProtocolInfo[0] & 1<<5) {
      printf (" * PICC to PCD, 1etu=32/fc, bitrate 424 kbits/s supported\n");
    }
    if (nbi.abtProtocolInfo[0] & 1<<6) {
      printf (" * PICC to PCD, 1etu=16/fc, bitrate 847 kbits/s supported\n");
    }
    if (nbi.abtProtocolInfo[0] & 1<<0) {
      printf (" * PCD to PICC, 1etu=64/fc, bitrate 212 kbits/s supported\n");
    }
    if (nbi.abtProtocolInfo[0] & 1<<1) {
      printf (" * PCD to PICC, 1etu=32/fc, bitrate 424 kbits/s supported\n");
    }
    if (nbi.abtProtocolInfo[0] & 1<<2) {
      printf (" * PCD to PICC, 1etu=16/fc, bitrate 847 kbits/s supported\n");
    }
    if (nbi.abtProtocolInfo[0] & 1<<3) {
      printf (" * ERROR unknown value\n");
    }
    if( (nbi.abtProtocolInfo[1] & 0xf0) <= 0x80 ) {
      printf ("* Maximum frame sizes: %d bytes\n", iMaxFrameSizes[((nbi.abtProtocolInfo[1] & 0xf0) >> 4)]);
    }
    if((nbi.abtProtocolInfo[1] & 0x0f) == PI_ISO14443_4_SUPPORTED) {
      printf ("* Protocol types supported: ISO/IEC 14443-4\n");
    }
    printf ("* Frame Waiting Time: %.4g ms\n",256.0*16.0*(1<<((nbi.abtProtocolInfo[2] & 0xf0) >> 4))/13560.0);
    if((nbi.abtProtocolInfo[2] & (PI_NAD_SUPPORTED|PI_CID_SUPPORTED)) != 0) {
      printf ("* Frame options supported: ");
      if ((nbi.abtProtocolInfo[2] & PI_NAD_SUPPORTED) != 0) printf ("NAD ");
      if ((nbi.abtProtocolInfo[2] & PI_CID_SUPPORTED) != 0) printf ("CID ");
      printf("\n");
    }
  }
}

void
print_nfc_dep_info (const nfc_dep_info_t ndi, bool verbose)
{
  printf ("       NFCID3: ");
  print_hex (ndi.abtNFCID3, 10);
  printf ("           BS: %02x\n", ndi.btBS);
  printf ("           BR: %02x\n", ndi.btBR);
  printf ("           TO: %02x\n", ndi.btTO);
  printf ("           PP: %02x\n", ndi.btPP);
  if (ndi.szGB) {
    printf ("General Bytes: ");
    print_hex (ndi.abtGB, ndi.szGB);
  }
}

/**
 * @brief Tries to parse arguments to find device descriptions.
 * @return Returns the list of found device descriptions.
 */
nfc_device_desc_t *
parse_args (int argc, const char *argv[], size_t * szFound, bool * verbose)
{
  nfc_device_desc_t *pndd = 0;
  int     arg;
  *szFound = 0;

  // Get commandline options
  for (arg = 1; arg < argc; arg++) {

    if (0 == strcmp (argv[arg], "--device")) {
      // FIXME: this device selection by command line options is terrible & does not support USB/PCSC drivers
      if (argc > arg + 1) {
        char    buffer[256];

        pndd = malloc (sizeof (nfc_device_desc_t));

        strncpy (buffer, argv[++arg], 256);

        // Driver.
        pndd->pcDriver = (char *) malloc (256);
        strcpy (pndd->pcDriver, strtok (buffer, ":"));

        // Port.
        pndd->pcPort = (char *) malloc (256);
        strcpy (pndd->pcPort, strtok (NULL, ":"));

        // Speed.
        sscanf (strtok (NULL, ":"), "%u", &pndd->uiSpeed);

        *szFound = 1;
      } else {
        errx (1, "usage: %s [--device driver:port:speed]", argv[0]);
      }
    }
    if ((0 == strcmp (argv[arg], "-v")) || (0 == strcmp (argv[arg], "--verbose"))) {
      *verbose = true;
    }
  }
  return pndd;
}

const char *
str_nfc_baud_rate (const nfc_baud_rate_t nbr)
{
  switch(nbr) {
    case NBR_UNDEFINED:
      return "undefined baud rate";
    break;
    case NBR_106:
      return "106 kbps";
    break;
    case NBR_212:
      return "212 kbps";
    break;
    case NBR_424:
      return "424 kbps";
    break;
    case NBR_847:
      return "847 kbps";
    break;
  }
  return "";
}

void
print_nfc_target (const nfc_target_t nt, bool verbose)
{
  switch(nt.nm.nmt) {
    case NMT_ISO14443A:
      printf ("ISO/IEC 14443A (%s) target:\n", str_nfc_baud_rate(nt.nm.nbr));
      print_nfc_iso14443a_info (nt.nti.nai, verbose);
    break;
    case NMT_JEWEL:
      printf ("Innovision Jewel (%s) target:\n", str_nfc_baud_rate(nt.nm.nbr));
      print_nfc_jewel_info (nt.nti.nji, verbose);
    break;
    case NMT_FELICA:
      printf ("FeliCa (%s) target:\n", str_nfc_baud_rate(nt.nm.nbr));
      print_nfc_felica_info (nt.nti.nfi, verbose);
    break;
    case NMT_ISO14443B:
      printf ("ISO/IEC 14443-4B (%s) target:\n", str_nfc_baud_rate(nt.nm.nbr));
      print_nfc_iso14443b_info (nt.nti.nbi, verbose);
    break;
    case NMT_DEP:
      printf ("D.E.P. (%s) target:\n", str_nfc_baud_rate(nt.nm.nbr));
      print_nfc_dep_info (nt.nti.ndi, verbose);
    break;
  }
}

