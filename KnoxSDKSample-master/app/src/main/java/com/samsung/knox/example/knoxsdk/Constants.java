/**
 * DISCLAIMER: PLEASE TAKE NOTE THAT THE SAMPLE APPLICATION AND
 * SOURCE CODE DESCRIBED HEREIN IS PROVIDED FOR TESTING PURPOSES ONLY.
 * <p>
 * Samsung expressly disclaims any and all warranties of any kind,
 * whether express or implied, including but not limited to the implied warranties and conditions
 * of merchantability, fitness for com.samsung.knoxsdksample particular purpose and non-infringement.
 * Further, Samsung does not represent or warrant that any portion of the sample application and
 * source code is free of inaccuracies, errors, bugs or interruptions, or is reliable,
 * accurate, complete, or otherwise valid. The sample application and source code is provided
 * "as is" and "as available", without any warranty of any kind from Samsung.
 * <p>
 * Your use of the sample application and source code is at its own discretion and risk,
 * and licensee will be solely responsible for any damage that results from the use of the sample
 * application and source code including, but not limited to, any damage to your computer system or
 * platform. For the purpose of clarity, the sample code is licensed “as is” and
 * licenses bears the risk of using it.
 * <p>
 * Samsung shall not be liable for any direct, indirect or consequential damages or
 * costs of any type arising out of any action taken by you or others related to the sample application
 * and source code.
 */

package com.samsung.knox.example.knoxsdk;

/**
 * Provide a valid KLM license as a constant. This technique is used for demonstration purposes
 * only.
 * Consider using more secure approach for passing your license key in a production scenario.
 * Visit https://seap.samsung.com/license-keys/about for details
 */

public interface Constants {
    /**
     * Note::
     * In the new licensing model, the three types of licenses available are:
     * - KPE Development Key
     * - KPE Commercial Key - Standard
     * - KPE Commercial Key - Premium
     *
     * The KPE Development and KPE Commercial Key - Standard can be generated from SEAP while a KPE
     * Commercial Key can be bought from a reseller.
     *
     * You can read more about licenses at: https://seap.samsung.com/license-keys/about-licenses
     *
     * TODO Enter the KPE license key for Knox SDK. You can obtain it from https://seap.samsung.com/sdk.
     * TODO Implement a secure mechanism to pass KPE key to your application
     */
    String KPE_LICENSE_KEY = "KLM06-IQ76R-LYD0B-9JBH8-AEMYC-ZAN7B";
}
