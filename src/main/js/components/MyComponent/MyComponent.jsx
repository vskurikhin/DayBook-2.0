import Frame, { FrameContextConsumer } from 'react-frame-component'
import ReactDOM from 'react-dom';

import RootDataViewLazy from '../RootDataViewLazy/RootDataViewLazy'

const MyComponent = (props, context) => (
  <Frame>
    <div id="root"></div>
    <FrameContextConsumer>
      {
        // Callback is invoked with iframe's window and document instances
        ({document, window}) => {
          // Render Children
          const rootElement = document.getElementById("root");
          ReactDOM.render(<RootDataViewLazy />, rootElement);
        }
      }
    </FrameContextConsumer>
  </Frame>
);

export default MyComponent;