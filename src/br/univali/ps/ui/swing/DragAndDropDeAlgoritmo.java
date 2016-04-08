package br.univali.ps.ui.swing;

import br.univali.ps.nucleo.PortugolStudio;
import java.awt.Image;
import java.awt.Point;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.InputEvent;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.TransferHandler;

/**
 *
 * @author noschang
 */
public class DragAndDropDeAlgoritmo extends TransferHandler
{
    private TransferHandler handlerPadrao = new NullHandler();

    public DragAndDropDeAlgoritmo()
    {
        super();
    }

    public DragAndDropDeAlgoritmo(TransferHandler handlerPadrao)
    {
        super();
        setHandlerPadrao(handlerPadrao);
    }
    
    @Override
    public boolean canImport(TransferHandler.TransferSupport support)
    {
        if (support.isDataFlavorSupported(DataFlavor.javaFileListFlavor))
        {
            return true;
        }
        
        return handlerPadrao.canImport(support);
    }

    @Override
    public boolean importData(TransferHandler.TransferSupport support)
    {
        if (support.isDataFlavorSupported(DataFlavor.javaFileListFlavor))            
        {

            Transferable transferable = support.getTransferable();

            try
            {
                List<File> fileList = (List<File>) transferable.getTransferData(DataFlavor.javaFileListFlavor);

                PortugolStudio.getInstancia().getTelaPrincipal().abrirArquivosCodigoFonte(fileList);

            }
            catch (UnsupportedFlavorException | IOException e)
            {
                return false;
            }

            return true;
        }
        else
        {
            return handlerPadrao.importData(support);
        }
    }

    @Override
    public void exportAsDrag(JComponent comp, InputEvent e, int action)
    {
        handlerPadrao.exportAsDrag(comp, e, action);
    }

    @Override
    public void exportToClipboard(JComponent comp, Clipboard clip, int action) throws IllegalStateException
    {
        handlerPadrao.exportToClipboard(comp, clip, action);
    }

    @Override
    public Image getDragImage()
    {
        return handlerPadrao.getDragImage();
    }

    @Override
    public Point getDragImageOffset()
    {
        return handlerPadrao.getDragImageOffset();
    }

    @Override
    public int getSourceActions(JComponent c)
    {
        return handlerPadrao.getSourceActions(c);
    }
    
    
    
    private void setHandlerPadrao(TransferHandler handlerPadrao)
    {
        if (handlerPadrao == null)
        {
            handlerPadrao = new NullHandler();
            
        }
        this.handlerPadrao = handlerPadrao;
    }
    
    private final class NullHandler extends TransferHandler
    {
        @Override
        public boolean canImport(TransferSupport support)
        {
            return false;
        }

        @Override
        public boolean importData(TransferSupport support)
        {
            return false;
        }
    }
}
