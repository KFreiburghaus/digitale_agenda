import {ModuleWithProviders, NgModule, Optional, SkipSelf} from '@angular/core';
import {CommonModule} from '@angular/common';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {Configuration} from './configuration';
import {AuthControllerService} from './api/authController.service';
import {BasicErrorControllerService} from './api/basicErrorController.service';
import {MainControllerService} from './api/mainController.service';
import {TerminControllerService} from './api/terminController.service';
import {TokenInterceptorService} from './token-interceptor.service';


@NgModule({
  imports: [CommonModule, HttpClientModule],
  declarations: [],
  exports: [],
  providers: [
    AuthControllerService,
    BasicErrorControllerService,
    MainControllerService,
    TerminControllerService,
    {provide: HTTP_INTERCEPTORS, useClass: TokenInterceptorService, multi: true }
  ]
})
export class ApiModule {
  public static forRoot(configurationFactory: () => Configuration): ModuleWithProviders {
    return {
      ngModule: ApiModule,
      providers: [{provide: Configuration, useFactory: configurationFactory}]
    };
  }

  constructor(@Optional() @SkipSelf() parentModule: ApiModule) {
    if (parentModule) {
      throw new Error('ApiModule is already loaded. Import your base AppModule only.');
    }
  }
}
