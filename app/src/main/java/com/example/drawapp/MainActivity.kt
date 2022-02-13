package com.example.drawapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.drawapp.data.DrawColors
import com.example.drawapp.data.Tools
import com.example.drawapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var layout: ActivityMainBinding
    private var currentSelectedTool = Tools.Pencil
    private var currentToolColor = DrawColors.Black
    private var colorPaletteVisibility = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        layout = ActivityMainBinding.inflate(layoutInflater)
        setContentView(layout.root)

        setToolsClickListener()

        updateSelectToolState()


    }


    /*********Set ClickListener for All the tools */
    private fun setToolsClickListener() {
        layout.toolColorPalette.setOnClickListener(toolsClickListener)
        layout.toolPencil.setOnClickListener(toolsClickListener)
        layout.toolArrow.setOnClickListener(toolsClickListener)
        layout.toolEllipse.setOnClickListener(toolsClickListener)
        layout.toolRect.setOnClickListener(toolsClickListener)
        layout.colorBlackButton.setOnClickListener(colorClickListener)
        layout.colorBlueButton.setOnClickListener(colorClickListener)
        layout.colorGreenButton.setOnClickListener(colorClickListener)
        layout.colorRedButton.setOnClickListener(colorClickListener)
    }

    /******* Make a ClickListener object for all the tools */
    private val toolsClickListener = View.OnClickListener { toolsId ->
        when (toolsId.id) {
            layout.toolPencil.id -> {
                currentSelectedTool = Tools.Pencil
                layout.drawCanvas.currentToolsState = currentSelectedTool
                updateSelectToolState()
            }
            layout.toolRect.id -> {
                currentSelectedTool = Tools.Rectangle
                layout.drawCanvas.currentToolsState = currentSelectedTool
                updateSelectToolState()

            }
            layout.toolArrow.id -> {
                currentSelectedTool = Tools.Arrow
                layout.drawCanvas.currentToolsState = currentSelectedTool
                updateSelectToolState()

            }
            layout.toolEllipse.id -> {
                currentSelectedTool = Tools.Ellipse
                layout.drawCanvas.currentToolsState = currentSelectedTool
                updateSelectToolState()

            }
            layout.toolColorPalette.id -> {
                showOrHideColorPalette()
            }


        }
    }

    /***** Make a ClickListener object for selected color from palette*/
    private val colorClickListener = View.OnClickListener { colorId ->
        when (colorId) {
            layout.colorBlackButton -> {
                currentToolColor = DrawColors.Black
                updateToolsColor()
                colorPaletteVisibility = true
                showOrHideColorPalette()
            }
            layout.colorBlueButton -> {
                currentToolColor = DrawColors.Blue
                updateToolsColor()
                colorPaletteVisibility = true
                showOrHideColorPalette()
            }
            layout.colorGreenButton -> {
                currentToolColor = DrawColors.Grren
                updateToolsColor()
                colorPaletteVisibility = true
                showOrHideColorPalette()
            }
            layout.colorRedButton -> {
                currentToolColor = DrawColors.Red
                updateToolsColor()
                colorPaletteVisibility = true
                showOrHideColorPalette()
            }
        }
    }

    /**** Make Color palette visible or invisible */
    private fun showOrHideColorPalette() {
        if (colorPaletteVisibility) {
            layout.colorContainer.visibility = View.GONE
            colorPaletteVisibility = false
        } else {
            layout.colorContainer.visibility = View.VISIBLE
            colorPaletteVisibility = true
        }
    }

    /**** change selected tool state*/
    private fun updateSelectToolState() {
        when (currentSelectedTool) {
            Tools.Ellipse -> {
                layout.toolEllipse.isSelected = true
                layout.toolEllipse.isEnabled = false
                layout.toolArrow.isEnabled = true
                layout.toolRect.isEnabled = true
                layout.toolPencil.isEnabled = true
            }
            Tools.Arrow -> {
                layout.toolArrow.isSelected = true
                layout.toolEllipse.isEnabled = true
                layout.toolArrow.isEnabled = false
                layout.toolRect.isEnabled = true
                layout.toolPencil.isEnabled = true

            }
            Tools.Rectangle -> {
                layout.toolRect.isSelected = true
                layout.toolEllipse.isEnabled = true
                layout.toolArrow.isEnabled = true
                layout.toolRect.isEnabled = false
                layout.toolPencil.isEnabled = true
            }
            Tools.Pencil -> {
                layout.toolPencil.isSelected = true
                layout.toolEllipse.isEnabled = true
                layout.toolArrow.isEnabled = true
                layout.toolRect.isEnabled = true
                layout.toolPencil.isEnabled = false
            }

        }
    }

    /***** Update Paint Color in Canvas*/
    private fun updateToolsColor() {
        when (currentToolColor) {
            DrawColors.Red -> {
                layout.drawCanvas.boxColor.color = resources.getColor(R.color.red, null)
            }
            DrawColors.Grren -> {
                layout.drawCanvas.boxColor.color = resources.getColor(R.color.green, null)
            }
            DrawColors.Blue -> {
                layout.drawCanvas.boxColor.color = resources.getColor(R.color.blue, null)
            }
            DrawColors.Black -> {
                layout.drawCanvas.boxColor.color = resources.getColor(R.color.black, null)
            }
        }
    }


}