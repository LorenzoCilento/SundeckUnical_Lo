package sundeckunical.core;

public class Score implements Comparable<Score>{

	private final String name;

	private final int score;
	
	public Score(final String toParse)
	{
	    final String[] split = toParse.split("-");
	    name = split[0];
	    score = Integer.parseInt(split[1]);
	}
	
	public Score(final String name, final int score)
	{
	    this.name = name;
	    this.score = score;
	}
	
	public String getName()
	{
	    return name;
	}
	
	public int getScore()
	{
	    return score;
	}
	
	@Override
	public String toString()
	{
	    return name + "-" + score;
	}
	
	@Override
	public int compareTo(Score o) {
		return o.getScore()-this.getScore();
	}

	@Override
	public boolean equals(Object obj) {
		if( this.getScore() == ((Score)obj).getScore() )
			return true;
		return false;
	}

}
