package net.sourceforge.javajson;

import java.io.IOException;
import java.io.Reader;
import java.nio.CharBuffer;
import java.util.Stack;

/**
 * acts like java.io.PushbackReader but without a pushback limit
 * 
 * @author mdeanda
 * 
 */
public class UnlimitedPushbackReader extends Reader {
	private Reader reader;
	private Stack<Character> pushback;

	public UnlimitedPushbackReader(Reader in) {
		this.reader = in;
		this.pushback = new Stack<Character>();
	}

	@Override
	public void close() throws IOException {
		pushback.clear();
		reader.close();
	}

	@Override
	public void mark(int readAheadLimit) throws IOException {
		throw new IOException("operation unsupported");
	}

	@Override
	public boolean markSupported() {
		return false;
	}

	@Override
	public int read() throws IOException {
		int ret = -1;
		if (!pushback.isEmpty())
			ret = pushback.pop();
		else
			ret = reader.read();
		return ret;
	}

	@Override
	public int read(char[] cbuf) throws IOException {
		// if we have anything in buffer, return only that
		int ret = 0;
		if (!pushback.isEmpty()) {
			do {
				cbuf[ret++] = pushback.pop();
			} while (!pushback.isEmpty() || ret == cbuf.length);
		} else
			ret = reader.read(cbuf);
		return ret;
	}

	/**
	 * throws ioexception, this operation is not supported (yet)
	 */
	@Override
	public int read(char[] cbuf, int off, int len) throws IOException {
		throw new IOException("operation unsupported");
	}

	@Override
	public int read(CharBuffer target) throws IOException {
		return reader.read(target);
	}

	@Override
	public boolean ready() throws IOException {
		return !pushback.isEmpty() || reader.ready();
	}

	@Override
	public void reset() throws IOException {
		throw new IOException("operation unsupported");
	}

	@Override
	public long skip(long n) throws IOException {
		int i = pushback.size();
		long ret = 0;
		if (n >= i) {
			// if we are skipping past pushback, clear pushback, reduce skip
			pushback.clear();
			n -= i;
			ret += i;
		} else {
			// if we are skipping within pusbhack, reduce pushback
			while (n-- > 0) {
				pushback.pop();
				ret++;
			}
		}
		ret += reader.skip(n);
		return ret;
	}

	public void unread(char ch) {
		pushback.push(ch);
	}

	public void unread(char[] cbuf) {
		for (int i = cbuf.length - 1; i >= 0; i--) {
			pushback.push(cbuf[i]);
		}
	}

	public void unread(char[] cbuf, int off, int len) {
		for (int i = off + len - 1; i >= off; i--) {
			pushback.push(cbuf[i]);
		}
	}
}
