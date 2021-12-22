package com.github.dinbtechit.es.ui.shared.textfield

import javax.swing.text.AttributeSet
import javax.swing.text.BadLocationException
import javax.swing.text.PlainDocument

class LengthRestrictedDocument(private val limit: Int) : PlainDocument() {
  @Throws(BadLocationException::class)
  override fun insertString(offs: Int, str: String?, a: AttributeSet?) {
    if (str == null) return
    if (length + str.length <= limit) {
      super.insertString(offs, str, a)
    }
  }
}