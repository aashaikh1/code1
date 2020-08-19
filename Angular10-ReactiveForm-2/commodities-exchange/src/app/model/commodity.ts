export class Commodity {
	preferred: boolean = false;

  constructor(public name: string,
              public symbol: string,
              public currentValue: number,
              public previousValue: number,
			  public market: string) {}

  public isUpwardChange = (): boolean => {
        return this.currentValue >= this.previousValue;
  }  
}
