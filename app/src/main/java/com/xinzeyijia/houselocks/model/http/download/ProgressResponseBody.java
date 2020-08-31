package com.xinzeyijia.houselocks.model.http.download;

import com.luck.picture.lib.rxbus2.RxBus;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;


/**
 * Created by miya95 on 2016/12/5.
 */
public class ProgressResponseBody extends ResponseBody {
    private ResponseBody responseBody;

    private BufferedSource bufferedSource;
    private String tag;
    public ProgressResponseBody(ResponseBody responseBody) {
        this.responseBody = responseBody;
    }
    public ProgressResponseBody(ResponseBody responseBody, String tag) {
        this.responseBody = responseBody;
        this.tag = tag;
    }
    @Override
    public MediaType contentType() {
        return responseBody.contentType();
    }

    @Override
    public long contentLength() {
        return responseBody.contentLength();
    }

    @Override
    public BufferedSource source() {
        if (bufferedSource == null) {
            bufferedSource = Okio.buffer( source( responseBody.source() ) );
        }
        return bufferedSource;
    }

    private Source source(Source source) {
        return new ForwardingSource( source ) {
            long bytesReaded = 0;

            @Override
            public long read(Buffer sink, long byteCount) throws IOException {
                long bytesRead = super.read( sink, byteCount );
                bytesReaded += bytesRead == -1 ? 0 : bytesRead;
                if (contentLength() > 100000000)
                RxBus.getDefault().post(new DownLoadStateBean( contentLength() / 1000, bytesReaded / 1000, tag));
                else  RxBus.getDefault().post(new DownLoadStateBean(  contentLength() / 100, bytesReaded / 100, tag));

                      return bytesRead;
            }
        };
    }
}
