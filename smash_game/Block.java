package assignment3;

import java.util.ArrayList;
import java.util.Random;
import java.awt.Color;

public class Block {
 private int xCoord;
 private int yCoord;
 private int size; // height/width of the square
 private int level; // the root (outer most block) is at level 0
 private int maxDepth;
 private Color color;

 private Block[] children; // {UR, UL, LL, LR}

 public static Random gen = new Random();


 /*
  * These two constructors are here for testing purposes.
  */
 public Block() {}

 public Block(int x, int y, int size, int lvl, int maxD, Color c, Block[] subBlocks) {
  this.xCoord=x;
  this.yCoord=y;
  this.size=size;
  this.level=lvl;
  this.maxDepth = maxD;
  this.color=c;
  this.children = subBlocks;
 }



 /*
  * Creates a random block given its level and a max depth.
  *
  * xCoord, yCoord, size, and highlighted should not be initialized
  * (i.e. they will all be initialized by default)
  */
 public Block(int lvl, int maxDepth) {
  /*
   * ADD YOUR CODE HERE
   */

  if (lvl > maxDepth) {
   throw new IllegalArgumentException("Level can't be greater than the Max Depth!");
  }

  this.level = lvl;
  this.maxDepth = maxDepth;

  if (lvl == maxDepth || gen.nextDouble() >= Math.exp(-0.25 * lvl)) {
   this.children = new Block[0];
   this.color = GameColors.BLOCK_COLORS[gen.nextInt(GameColors.BLOCK_COLORS.length)];
  } else {
   this.children = new Block[4];
   for (int i = 0; i < 4; i++) {
    this.children[i] = new Block(lvl + 1, maxDepth);
   }
  }
 }


 /*
  * Updates size and position for the block and all of its sub-blocks, while
  * ensuring consistency between the attributes and the relationship of the
  * blocks.
  *
  *  The size is the height and width of the block. (xCoord, yCoord) are the
  *  coordinates of the top left corner of the block.
  */
 public void updateSizeAndPosition(int size, int xCoord, int yCoord) {

  validateSize(size);
  this.xCoord = xCoord;
  this.yCoord = yCoord;
  this.size = size;
  if (hasChildren()) {
   updateChildSizeAndPosition(size, xCoord, yCoord);
  }
 }

 private void validateSize(int size) {
  if (size != 1) {
   if (size <= 0 || size % 2 != 0) {
    throw new IllegalArgumentException("Invalid Size!");
   }
  }
 }

 private boolean hasChildren() {
  return this.children.length != 0;
 }

 private void updateChildSizeAndPosition(int size, int xCoord, int yCoord) {
  int halfSize = size / 2;
  this.children[0].updateSizeAndPosition(halfSize, xCoord + halfSize, yCoord);
  this.children[1].updateSizeAndPosition(halfSize, xCoord, yCoord);
  this.children[2].updateSizeAndPosition(halfSize, xCoord, yCoord + halfSize);
  this.children[3].updateSizeAndPosition(halfSize, xCoord + halfSize, yCoord + halfSize);
 }


 /*
  * Returns a List of blocks to be drawn to get a graphical representation of this block.
  *
  * This includes, for each undivided Block:
  * - one BlockToDraw in the color of the block
  * - another one in the FRAME_COLOR and stroke thickness 3
  *
  * Note that a stroke thickness equal to 0 indicates that the block should be filled with its color.
  *
  * The order in which the blocks to draw appear in the list does NOT matter.
  */
 public ArrayList<BlockToDraw> getBlocksToDraw() {
  ArrayList<BlockToDraw> blocks = new ArrayList<>();
  if (this.children.length == 0) {
   blocks.add(new BlockToDraw(GameColors.FRAME_COLOR, this.xCoord, this.yCoord, this.size, 3));
   blocks.add(new BlockToDraw(this.color, this.xCoord, this.yCoord, this.size, 0));
  } else{
   for (Block block : this.children){
    blocks.addAll(block.getBlocksToDraw());
   }
  }

  return blocks;
 }

 public BlockToDraw getHighlightedFrame() {
  return new BlockToDraw(GameColors.HIGHLIGHT_COLOR, this.xCoord, this.yCoord, this.size, 5);
 }



 /*
  * Return the Block within this Block that includes the given location
  * and is at the given level. If the level specified is lower than
  * the lowest block at the specified location, then return the block
  * at the location with the closest level value.
  *
  * The location is specified by its (x, y) coordinates. The lvl indicates
  * the level of the desired Block. Note that if a Block includes the location
  * (x, y), and that Block is subdivided, then one of its sub-Blocks will
  * contain the location (x, y) too. This is why we need lvl to identify
  * which Block should be returned.
  *
  * Input validation:
  * - this.level <= lvl <= maxDepth (if not throw exception)
  * - if (x,y) is not within this Block, return null.
  */
 public Block getSelectedBlock(int x, int y, int lvl) {
  /*
   * ADD YOUR CODE HERE
   */
  if (lvl < this.level || lvl > maxDepth) {
   throw new IllegalArgumentException("Invalid Level!");
  }

  Block selectedBlock = null;
  if (this.level < lvl && this.children.length != 0) {
   for (Block block : this.children) {
    selectedBlock = block.getSelectedBlock(x, y, lvl);
    if (selectedBlock != null) {
     break;
    }
   }
  } else {
   if (x >= this.xCoord && x < this.xCoord + this.size && y >= this.yCoord && y < this.yCoord + this.size) {
    selectedBlock = this;
   }
  }

  return selectedBlock;
 }

 public void turnUp() {
  this.yCoord -= this.size;
  this.updateSizeAndPosition(this.size, this.xCoord, this.yCoord);
 }

 public void turnDown() {
  this.yCoord += this.size;
  this.updateSizeAndPosition(this.size, this.xCoord, this.yCoord);
 }

 public void turnLeft() {
  this.xCoord -= this.size;
  this.updateSizeAndPosition(this.size, this.xCoord, this.yCoord);
 }

 public void turnRight() {
  this.xCoord += this.size;
  this.updateSizeAndPosition(this.size, this.xCoord, this.yCoord);
 }

 /*
  * Swaps the child Blocks of this Block.
  * If input is 1, swap vertically. If 0, swap horizontally.
  * If this Block has no children, do nothing. The swap
  * should be propagate, effectively implementing a reflection
  * over the x-axis or over the y-axis.
  *
  */
 public void reflect(int direction) {
  if (direction != 0 && direction != 1) {
   throw new IllegalArgumentException("Invalid Direction!");
  }

  reflectHelper(this, direction);
  //this.color = this.children[0].color;
 }

 private void reflectHelper(Block block, int direction) {
  if (block.children.length == 0) {
   return;
  }

  if (direction == 0) {
   block.children[0].turnDown();
   block.children[1].turnDown();
   block.children[2].turnUp();
   block.children[3].turnUp();

   Block tmp = block.children[0];
   block.children[0] = block.children[3];
   block.children[3] = tmp;

   tmp = block.children[1];
   block.children[1] = block.children[2];
   block.children[2] = tmp;
  } else {
   block.children[0].turnLeft();
   block.children[1].turnRight();
   block.children[2].turnRight();
   block.children[3].turnLeft();

   Block tmp = block.children[0];
   block.children[0] = block.children[1];
   block.children[1] = tmp;

   tmp = block.children[2];
   block.children[2] = block.children[3];
   block.children[3] = tmp;
  }

  for (Block child : block.children) {
   reflectHelper(child, direction);
  }
 }


 /*
  * Rotate this Block and all its descendants.
  * If the input is 1, rotate clockwise. If 0, rotate
  * counterclockwise. If this Block has no children, do nothing.
  */
 public void rotate(int direction) {
  /*
   * ADD YOUR CODE HERE
   */
  if (direction != 0 && direction != 1) {
   throw new IllegalArgumentException("Invalid Direction!");
  }
  if (this.children.length == 0) return;
  if (direction == 1) {
   this.children[0].turnDown();
   this.children[1].turnRight();
   this.children[2].turnUp();
   this.children[3].turnLeft();

   Block tmp;
   tmp = this.children[0]; this.children[0] = this.children[1]; this.children[1] = this.children[2];
   this.children[2] = this.children[3]; this.children[3] = tmp;
  } else {
   this.children[0].turnLeft();
   this.children[1].turnDown();
   this.children[2].turnRight();
   this.children[3].turnUp();

   Block tmp;
   tmp = this.children[3]; this.children[3] = this.children[2]; this.children[2] = this.children[1];
   this.children[1] = this.children[0]; this.children[0] = tmp;
  }
  for (Block block : this.children) {
   block.rotate(direction);
  }
 }



 /*
  * Smash this Block.
  *
  * If this Block can be smashed,
  * randomly generate four new children Blocks for it.
  * (If it already had children Blocks, discard them.)
  * Ensure that the invariants of the Blocks remain satisfied.
  *
  * A Block can be smashed iff it is not the top-level Block
  * and it is not already at the level of the maximum depth.
  *
  * Return True if this Block was smashed and False otherwise.
  *
  */

 public boolean smash() {
  /*
   * ADD YOUR CODE HERE
   */

  if (this.level == 0 || this.level == this.maxDepth) {
   return false;
  }

  Block[] blocks = new Block[4];

  for (int i = 0; i < 4; i++) {
   Block block = new Block();
   block.level = this.level + 1;
   block.maxDepth = this.maxDepth;
   block.color = GameColors.BLOCK_COLORS[gen.nextInt(GameColors.BLOCK_COLORS.length)];
   block.size = this.size / 2;

   switch (i) {
    case 0: {
     block.xCoord = this.xCoord + this.size / 2;
     block.yCoord = this.yCoord;
     break;
    }
    case 1: {
     block.xCoord = this.xCoord;
     block.yCoord = this.yCoord;
     break;
    }
    case 2: {
     block.xCoord = this.xCoord;
     block.yCoord = this.yCoord + this.size / 2;
     break;
    }
    case 3: {
     block.xCoord = this.xCoord + this.size / 2;
     block.yCoord = this.yCoord + this.size / 2;
     break;
    }
   }

   block.children = new Block[0];
   blocks[i] = block;
  }

  this.children = blocks;
  return true;
 }


 /*
  * Return a two-dimensional array representing this Block as rows and columns of unit cells.
  *
  * Return and array arr where, arr[i] represents the unit cells in row i,
  * arr[i][j] is the color of unit cell in row i and column j.
  *
  * arr[0][0] is the color of the unit cell in the upper left corner of this Block.
  */
 public Color[][] flatten() {
  /*
   * ADD YOUR CODE HERE
   */

  int size = (int)Math.pow(2, this.maxDepth - this.level);
  Color[][] colors = new Color[size][size];

  if(this.children.length == 0) {
   for(int i = 0; i < colors.length; i++) {
    for(int j = 0; j < colors[0].length; j++) {
     colors[i][j] = this.color;
    }
   }
  } else {
   Color[][] tmp0 = this.children[0].flatten();
   Color[][] tmp1 = this.children[1].flatten();
   Color[][] tmp2 = this.children[2].flatten();
   Color[][] tmp3 = this.children[3].flatten();

   int row = tmp0.length;
   int col = tmp0[0].length;

   for(int i = 0; i < row; i++) {
    System.arraycopy(tmp1[i], 0, colors[i], 0, col);
   }

   for(int i = 0; i < row; i++) {
    if (2 * col - col >= 0) {
     System.arraycopy(tmp0[i], 0, colors[i], col, 2 * col - col);
    }
   }

   for(int i = row; i < 2*row; i++) {
    System.arraycopy(tmp2[i - row], 0, colors[i], 0, col);
   }

   for(int i = row; i < row*2; i++) {
    if (col * 2 - col >= 0) {
     System.arraycopy(tmp3[i - row], 0, colors[i], col, col * 2 - col);
    }
   }
  }
  return colors;
 }


 // These two get methods have been provided. Do NOT modify them.
 public int getMaxDepth() {
  return this.maxDepth;
 }

 public int getLevel() {
  return this.level;
 }



 public String toString() {
  return String.format("pos=(%d,%d), size=%d, level=%d"
          , this.xCoord, this.yCoord, this.size, this.level);
 }

 public void printBlock() {
  this.printBlockIndented(0);
 }

 private void printBlockIndented(int indentation) {
  String indent = "";
  for (int i=0; i<indentation; i++) {
   indent += "\t";
  }

  if (this.children.length == 0) {
   // it's a leaf. Print the color!
   String colorInfo = GameColors.colorToString(this.color) + ", ";
   System.out.println(indent + colorInfo + this);
  } else {
   System.out.println(indent + this);
   for (Block b : this.children)
    b.printBlockIndented(indentation + 1);
  }
 }

 private static void coloredPrint(String message, Color color) {
  System.out.print(GameColors.colorToANSIColor(color));
  System.out.print(message);
  System.out.print(GameColors.colorToANSIColor(Color.WHITE));
 }

 public void printColoredBlock(){
  Color[][] colorArray = this.flatten();
  for (Color[] colors : colorArray) {
   for (Color value : colors) {
    String colorName = GameColors.colorToString(value).toUpperCase();
    if(colorName.length() == 0){
     colorName = "\u2588";
    }else{
     colorName = colorName.substring(0, 1);
    }
    coloredPrint(colorName, value);
   }
   System.out.println();
  }
 }

}

