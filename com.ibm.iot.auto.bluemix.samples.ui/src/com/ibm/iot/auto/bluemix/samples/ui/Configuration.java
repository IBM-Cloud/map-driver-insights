/**
 * Copyright 2016 IBM Corp. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the �gLicense�h);
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an �gAS IS�h BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ibm.iot.auto.bluemix.samples.ui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.json.JSONException;
import org.json.JSONObject;

public class Configuration {

	private String apiBaseUrl;
	private String tenantId;
	private String userName;
	private String password;
	
	public Configuration(String configFileName) throws JSONException, IOException {
		File configFile = null;
		FileInputStream fileInputStream = null;
		InputStreamReader inputStreamReader = null;
		BufferedReader bufferedReader = null;
		try {
			configFile = new File(configFileName);
			fileInputStream = new FileInputStream(configFile);
			inputStreamReader = new InputStreamReader(fileInputStream);
			bufferedReader = new BufferedReader(inputStreamReader);
			String jsonString = "";
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				jsonString += line;
			}
			JSONObject jsonObject = new JSONObject(jsonString);
			this.apiBaseUrl = jsonObject.getString("api_base_url");
			this.tenantId = jsonObject.getString("tenant_id");
			this.userName = jsonObject.getString("user_name");
			this.password = jsonObject.getString("password");
		} finally {
			if (bufferedReader != null) {
				bufferedReader.close();
			}
			if (inputStreamReader != null) {
				inputStreamReader.close();				
			}
			if (fileInputStream != null) {
				fileInputStream.close();
			}
		}
	}
	
	public String apiBaseUrl() {
		return apiBaseUrl;
	}

	public String tenantId() {
		return tenantId;
	}

	public String userName() {
		return userName;
	}

	public String password() {
		return password;
	}
	
}
