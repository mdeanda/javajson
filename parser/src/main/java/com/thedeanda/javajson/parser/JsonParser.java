/* JsonParser.java */
/* Generated By:JJTree&JavaCC: Do not edit this line. JsonParser.java */
package com.thedeanda.javajson.parser;

public class JsonParser/*@bgen(jjtree)*/implements JsonParserTreeConstants, JsonParserConstants {/*@bgen(jjtree)*/
  protected JJTJsonParserState jjtree = new JJTJsonParserState();public static void main(String args[]) throws Exception {
    System.out.println("Reading from standard input...");
    JsonParser t = new JsonParser(System.in);
        SimpleNode n = t.parse();
        n.dump("");
  }

  final public SimpleNode parse() throws ParseException {/*@bgen(jjtree) parse */
  ASTparse jjtn000 = new ASTparse(this, JJTPARSE);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);
    try {
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case 22:{
        object();
        jj_consume_token(0);
jjtree.closeNodeScope(jjtn000, true);
                     jjtc000 = false;
{if ("" != null) return jjtn000;}
        break;
        }
      case 24:{
        array();
        jj_consume_token(0);
jjtree.closeNodeScope(jjtn000, true);
                    jjtc000 = false;
{if ("" != null) return jjtn000;}
        break;
        }
      default:
        jj_la1[0] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    } catch (Throwable jjte000) {
if (jjtc000) {
      jjtree.clearNodeScope(jjtn000);
      jjtc000 = false;
    } else {
      jjtree.popNode();
    }
    if (jjte000 instanceof RuntimeException) {
      {if (true) throw (RuntimeException)jjte000;}
    }
    if (jjte000 instanceof ParseException) {
      {if (true) throw (ParseException)jjte000;}
    }
    {if (true) throw (Error)jjte000;}
    } finally {
if (jjtc000) {
      jjtree.closeNodeScope(jjtn000, true);
    }
    }
    throw new Error("Missing return statement in function");
  }

  final public void object() throws ParseException {/*@bgen(jjtree) object */
  ASTobject jjtn000 = new ASTobject(this, JJTOBJECT);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);
    try {
      jj_consume_token(22);
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case KEY_LITERAL:
      case STRING_LITERAL:
      case STRING_LITERAL_SINGLE_QUOTES:{
        members();
        break;
        }
      default:
        jj_la1[1] = jj_gen;
        ;
      }
      jj_consume_token(23);
    } catch (Throwable jjte000) {
if (jjtc000) {
      jjtree.clearNodeScope(jjtn000);
      jjtc000 = false;
    } else {
      jjtree.popNode();
    }
    if (jjte000 instanceof RuntimeException) {
      {if (true) throw (RuntimeException)jjte000;}
    }
    if (jjte000 instanceof ParseException) {
      {if (true) throw (ParseException)jjte000;}
    }
    {if (true) throw (Error)jjte000;}
    } finally {
if (jjtc000) {
      jjtree.closeNodeScope(jjtn000, true);
    }
    }
  }

  final public void array() throws ParseException {/*@bgen(jjtree) array */
  ASTarray jjtn000 = new ASTarray(this, JJTARRAY);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);
    try {
      jj_consume_token(24);
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case INTEGER_LITERAL:
      case FLOATING_POINT_LITERAL:
      case TRUE_LITERAL:
      case FALSE_LITERAL:
      case NULL_LITERAL:
      case STRING_LITERAL:
      case STRING_LITERAL_SINGLE_QUOTES:
      case 22:
      case 24:{
        elements();
        break;
        }
      default:
        jj_la1[2] = jj_gen;
        ;
      }
      jj_consume_token(25);
    } catch (Throwable jjte000) {
if (jjtc000) {
      jjtree.clearNodeScope(jjtn000);
      jjtc000 = false;
    } else {
      jjtree.popNode();
    }
    if (jjte000 instanceof RuntimeException) {
      {if (true) throw (RuntimeException)jjte000;}
    }
    if (jjte000 instanceof ParseException) {
      {if (true) throw (ParseException)jjte000;}
    }
    {if (true) throw (Error)jjte000;}
    } finally {
if (jjtc000) {
      jjtree.closeNodeScope(jjtn000, true);
    }
    }
  }

  final public void members() throws ParseException {/*@bgen(jjtree) members */
  ASTmembers jjtn000 = new ASTmembers(this, JJTMEMBERS);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);
    try {
      key();
      jj_consume_token(26);
      value();
      label_1:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
        case 27:{
          ;
          break;
          }
        default:
          jj_la1[3] = jj_gen;
          break label_1;
        }
        jj_consume_token(27);
        key();
        jj_consume_token(26);
        value();
      }
    } catch (Throwable jjte000) {
if (jjtc000) {
      jjtree.clearNodeScope(jjtn000);
      jjtc000 = false;
    } else {
      jjtree.popNode();
    }
    if (jjte000 instanceof RuntimeException) {
      {if (true) throw (RuntimeException)jjte000;}
    }
    if (jjte000 instanceof ParseException) {
      {if (true) throw (ParseException)jjte000;}
    }
    {if (true) throw (Error)jjte000;}
    } finally {
if (jjtc000) {
      jjtree.closeNodeScope(jjtn000, true);
    }
    }
  }

  final public void elements() throws ParseException {/*@bgen(jjtree) elements */
  ASTelements jjtn000 = new ASTelements(this, JJTELEMENTS);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);
    try {
      value();
      label_2:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
        case 27:{
          ;
          break;
          }
        default:
          jj_la1[4] = jj_gen;
          break label_2;
        }
        jj_consume_token(27);
        value();
      }
    } catch (Throwable jjte000) {
if (jjtc000) {
      jjtree.clearNodeScope(jjtn000);
      jjtc000 = false;
    } else {
      jjtree.popNode();
    }
    if (jjte000 instanceof RuntimeException) {
      {if (true) throw (RuntimeException)jjte000;}
    }
    if (jjte000 instanceof ParseException) {
      {if (true) throw (ParseException)jjte000;}
    }
    {if (true) throw (Error)jjte000;}
    } finally {
if (jjtc000) {
      jjtree.closeNodeScope(jjtn000, true);
    }
    }
  }

  final public void value() throws ParseException {/*@bgen(jjtree) value */
  ASTvalue jjtn000 = new ASTvalue(this, JJTVALUE);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);Token t;
    try {
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case STRING_LITERAL:
      case STRING_LITERAL_SINGLE_QUOTES:{
        string();
        break;
        }
      case INTEGER_LITERAL:
      case FLOATING_POINT_LITERAL:{
        number();
        break;
        }
      case 22:{
        object();
        break;
        }
      case 24:{
        array();
        break;
        }
      case TRUE_LITERAL:{
        t = jj_consume_token(TRUE_LITERAL);
jjtree.closeNodeScope(jjtn000, true);
                       jjtc000 = false;
jjtn000.val = t.image;
        break;
        }
      case FALSE_LITERAL:{
        t = jj_consume_token(FALSE_LITERAL);
jjtree.closeNodeScope(jjtn000, true);
                        jjtc000 = false;
jjtn000.val = t.image;
        break;
        }
      case NULL_LITERAL:{
        t = jj_consume_token(NULL_LITERAL);
jjtree.closeNodeScope(jjtn000, true);
                       jjtc000 = false;
jjtn000.val = t.image;
        break;
        }
      default:
        jj_la1[5] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    } catch (Throwable jjte000) {
if (jjtc000) {
      jjtree.clearNodeScope(jjtn000);
      jjtc000 = false;
    } else {
      jjtree.popNode();
    }
    if (jjte000 instanceof RuntimeException) {
      {if (true) throw (RuntimeException)jjte000;}
    }
    if (jjte000 instanceof ParseException) {
      {if (true) throw (ParseException)jjte000;}
    }
    {if (true) throw (Error)jjte000;}
    } finally {
if (jjtc000) {
      jjtree.closeNodeScope(jjtn000, true);
    }
    }
  }

  final public void key() throws ParseException {/*@bgen(jjtree) key */
  ASTkey jjtn000 = new ASTkey(this, JJTKEY);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);Token t;
    try {
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case KEY_LITERAL:{
        t = jj_consume_token(KEY_LITERAL);
jjtree.closeNodeScope(jjtn000, true);
                      jjtc000 = false;
jjtn000.val = t.image;
        break;
        }
      case STRING_LITERAL:{
        t = jj_consume_token(STRING_LITERAL);
jjtree.closeNodeScope(jjtn000, true);
                         jjtc000 = false;
jjtn000.val = t.image;
        break;
        }
      case STRING_LITERAL_SINGLE_QUOTES:{
        t = jj_consume_token(STRING_LITERAL_SINGLE_QUOTES);
jjtree.closeNodeScope(jjtn000, true);
                                       jjtc000 = false;
jjtn000.val = t.image;
        break;
        }
      default:
        jj_la1[6] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    } finally {
if (jjtc000) {
      jjtree.closeNodeScope(jjtn000, true);
    }
    }
  }

  final public void string() throws ParseException {/*@bgen(jjtree) string */
  ASTstring jjtn000 = new ASTstring(this, JJTSTRING);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);Token t;
    try {
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case STRING_LITERAL:{
        t = jj_consume_token(STRING_LITERAL);
jjtree.closeNodeScope(jjtn000, true);
                         jjtc000 = false;
jjtn000.val = t.image;
        break;
        }
      case STRING_LITERAL_SINGLE_QUOTES:{
        t = jj_consume_token(STRING_LITERAL_SINGLE_QUOTES);
jjtree.closeNodeScope(jjtn000, true);
                                       jjtc000 = false;
jjtn000.val = t.image;
        break;
        }
      default:
        jj_la1[7] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    } finally {
if (jjtc000) {
      jjtree.closeNodeScope(jjtn000, true);
    }
    }
  }

  final public void number() throws ParseException {/*@bgen(jjtree) number */
  ASTnumber jjtn000 = new ASTnumber(this, JJTNUMBER);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);Token t;
    try {
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case INTEGER_LITERAL:{
        t = jj_consume_token(INTEGER_LITERAL);
jjtree.closeNodeScope(jjtn000, true);
                          jjtc000 = false;
jjtn000.val = t.image;
        break;
        }
      case FLOATING_POINT_LITERAL:{
        t = jj_consume_token(FLOATING_POINT_LITERAL);
jjtree.closeNodeScope(jjtn000, true);
                                 jjtc000 = false;
jjtn000.val = t.image;
        break;
        }
      default:
        jj_la1[8] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    } finally {
if (jjtc000) {
      jjtree.closeNodeScope(jjtn000, true);
    }
    }
  }

  /** Generated Token Manager. */
  public JsonParserTokenManager token_source;
  JavaCharStream jj_input_stream;
  /** Current token. */
  public Token token;
  /** Next token. */
  public Token jj_nt;
  private int jj_ntk;
  private int jj_gen;
  final private int[] jj_la1 = new int[9];
  static private int[] jj_la1_0;
  static {
      jj_la1_init_0();
   }
   private static void jj_la1_init_0() {
      jj_la1_0 = new int[] {0x1400000,0x380000,0x1774200,0x8000000,0x8000000,0x1774200,0x380000,0x300000,0x4200,};
   }

  /** Constructor with InputStream. */
  public JsonParser(java.io.InputStream stream) {
     this(stream, null);
  }
  /** Constructor with InputStream and supplied encoding */
  public JsonParser(java.io.InputStream stream, String encoding) {
    try { jj_input_stream = new JavaCharStream(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source = new JsonParserTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 9; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream) {
     ReInit(stream, null);
  }
  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream, String encoding) {
    try { jj_input_stream.ReInit(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jjtree.reset();
    jj_gen = 0;
    for (int i = 0; i < 9; i++) jj_la1[i] = -1;
  }

  /** Constructor. */
  public JsonParser(java.io.Reader stream) {
    jj_input_stream = new JavaCharStream(stream, 1, 1);
    token_source = new JsonParserTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 9; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(java.io.Reader stream) {
    jj_input_stream.ReInit(stream, 1, 1);
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jjtree.reset();
    jj_gen = 0;
    for (int i = 0; i < 9; i++) jj_la1[i] = -1;
  }

  /** Constructor with generated Token Manager. */
  public JsonParser(JsonParserTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 9; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(JsonParserTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jjtree.reset();
    jj_gen = 0;
    for (int i = 0; i < 9; i++) jj_la1[i] = -1;
  }

  private Token jj_consume_token(int kind) throws ParseException {
    Token oldToken;
    if ((oldToken = token).next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    if (token.kind == kind) {
      jj_gen++;
      return token;
    }
    token = oldToken;
    jj_kind = kind;
    throw generateParseException();
  }


/** Get the next Token. */
  final public Token getNextToken() {
    if (token.next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    jj_gen++;
    return token;
  }

/** Get the specific Token. */
  final public Token getToken(int index) {
    Token t = token;
    for (int i = 0; i < index; i++) {
      if (t.next != null) t = t.next;
      else t = t.next = token_source.getNextToken();
    }
    return t;
  }

  private int jj_ntk_f() {
    if ((jj_nt=token.next) == null)
      return (jj_ntk = (token.next=token_source.getNextToken()).kind);
    else
      return (jj_ntk = jj_nt.kind);
  }

  private java.util.List<int[]> jj_expentries = new java.util.ArrayList<int[]>();
  private int[] jj_expentry;
  private int jj_kind = -1;

  /** Generate ParseException. */
  public ParseException generateParseException() {
    jj_expentries.clear();
    boolean[] la1tokens = new boolean[28];
    if (jj_kind >= 0) {
      la1tokens[jj_kind] = true;
      jj_kind = -1;
    }
    for (int i = 0; i < 9; i++) {
      if (jj_la1[i] == jj_gen) {
        for (int j = 0; j < 32; j++) {
          if ((jj_la1_0[i] & (1<<j)) != 0) {
            la1tokens[j] = true;
          }
        }
      }
    }
    for (int i = 0; i < 28; i++) {
      if (la1tokens[i]) {
        jj_expentry = new int[1];
        jj_expentry[0] = i;
        jj_expentries.add(jj_expentry);
      }
    }
    int[][] exptokseq = new int[jj_expentries.size()][];
    for (int i = 0; i < jj_expentries.size(); i++) {
      exptokseq[i] = jj_expentries.get(i);
    }
    return new ParseException(token, exptokseq, tokenImage);
  }

  /** Enable tracing. */
  final public void enable_tracing() {
  }

  /** Disable tracing. */
  final public void disable_tracing() {
  }

}
