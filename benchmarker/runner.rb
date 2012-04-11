class FixedHitCountCondition
  def initialize(count)
    @count= count
  end

  def call(runner)
    runner.hits >= @count
  end
end

class FixedTimeCondition
  def initialize(time_limit_sec)
    @time_limit_sec= time_limit_sec
  end

  def call(runner)
    (Time.now - runner.time_start) >= @time_limit_sec
  end
end

class Runner
  attr_reader :hits, :time_start, :time_end, :duration_sec

  def initialize(hit_proc, stop_condition)
    @hit_proc= hit_proc
    @stop_condition= stop_condition
    reset
  end

  def reset
    @hits= 0
    @time_start= @time_end= @duration_sec= nil
  end

  def run
    @time_start= Time.now
    until @hits > 0 and stop_running?
      @hit_proc.call
      @hits+= 1
    end
    @time_end= Time.now
    @duration_sec= time_end - time_start
  end

  def stop_running?
    @stop_condition.call(self)
  end

  def hits_per_sec
    hits / duration_sec
  end

  def duration_min
    duration_sec/60
  end
end

