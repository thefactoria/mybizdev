/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { MybizdevTestModule } from '../../../test.module';
import { BizDevDetailComponent } from '../../../../../../main/webapp/app/entities/biz-dev/biz-dev-detail.component';
import { BizDevService } from '../../../../../../main/webapp/app/entities/biz-dev/biz-dev.service';
import { BizDev } from '../../../../../../main/webapp/app/entities/biz-dev/biz-dev.model';

describe('Component Tests', () => {

    describe('BizDev Management Detail Component', () => {
        let comp: BizDevDetailComponent;
        let fixture: ComponentFixture<BizDevDetailComponent>;
        let service: BizDevService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MybizdevTestModule],
                declarations: [BizDevDetailComponent],
                providers: [
                    BizDevService
                ]
            })
            .overrideTemplate(BizDevDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BizDevDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BizDevService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new BizDev(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.bizDev).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
