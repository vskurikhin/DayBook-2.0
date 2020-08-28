import React, { useState } from 'react'
import { createPortal } from 'react-dom'

const IFrame = ({ children, ...props }) => {
  const [contentRef, setContentRef] = useState(null)
  const mountNode = contentRef && contentRef.contentWindow.document.body

  return (
    <iframe {...props} ref={setContentRef}>
      {mountNode &&
        createPortal(
          React.Children.only(children),
          mountNode
        )}
    </iframe>
  )
}
export default IFrame;