/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { MybizdevTestModule } from '../../../test.module';
import { BizDevComponent } from '../../../../../../main/webapp/app/entities/biz-dev/biz-dev.component';
import { BizDevService } from '../../../../../../main/webapp/app/entities/biz-dev/biz-dev.service';
import { BizDev } from '../../../../../../main/webapp/app/entities/biz-dev/biz-dev.model';

describe('Component Tests', () => {

    describe('BizDev Management Component', () => {
        let comp: BizDevComponent;
        let fixture: ComponentFixture<BizDevComponent>;
        let service: BizDevService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MybizdevTestModule],
                declarations: [BizDevComponent],
                providers: [
                    BizDevService
                ]
            })
            .overrideTemplate(BizDevComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BizDevComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BizDevService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new BizDev(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.bizDevs[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
